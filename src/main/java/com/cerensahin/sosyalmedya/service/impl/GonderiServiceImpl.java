package com.cerensahin.sosyalmedya.service.impl;

import com.cerensahin.sosyalmedya.dto.*;
import com.cerensahin.sosyalmedya.entity.*;
import com.cerensahin.sosyalmedya.ortak.exception.gonderi.BegeniZatenVarException;
import com.cerensahin.sosyalmedya.ortak.exception.gonderi.BegeniBulunamadiException;
import com.cerensahin.sosyalmedya.ortak.exception.gonderi.GonderiBulunamadiException;
import com.cerensahin.sosyalmedya.ortak.exception.gonderi.GonderiIslemYetkisizException;
import com.cerensahin.sosyalmedya.ortak.exception.medya.MedyaCokBuyukException;
import com.cerensahin.sosyalmedya.ortak.exception.medya.MedyaGecersizException;
import com.cerensahin.sosyalmedya.ortak.exception.yorum.YorumEkleGonderiBulunamadiException;
import com.cerensahin.sosyalmedya.repository.*;
import com.cerensahin.sosyalmedya.service.GonderiService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class GonderiServiceImpl implements GonderiService {

    private final GonderiRepository gonderiRepository;
    private final YorumRepository yorumRepository;
    private final PostBegeniRepository postBegeniRepository;

    private static final int MAX_BASE64_LEN = 7_000_000;


    @Override
    public Page<GonderiGorunum> liste(int page, int size) {
        return gonderiRepository
                .findAllByOrderByOlusturmaZamaniDesc(PageRequest.of(page, size))
                .map(GonderiGorunum::new);
    }

    @Override
    public Page<GonderiGorunum> kullaniciGonderileri(Long kullaniciId, int page, int size) {
        return gonderiRepository
                .findByKullanici_IdOrderByOlusturmaZamaniDesc(kullaniciId, PageRequest.of(page, size))
                .map(GonderiGorunum::new);
    }

    @Override
    public GonderiDetay detay(Long id, int page, int size) {
        Gonderi g = findPostOr404(id);
        long begeni = postBegeniRepository.countByGonderi_Id(id);
        var yorumPage = yorumRepository
                .findByGonderi_IdOrderByOlusturmaZamaniAsc(id, PageRequest.of(page, size))
                .map(YorumGorunum::new);
        return new GonderiDetay(g, begeni, yorumPage.getTotalElements(), yorumPage.getContent());
    }


    @Override
    public Map<String, Object> olustur(Kullanici aktif, GonderiOlusturIstegi body) {
        if (body.getIcerik() == null || body.getIcerik().trim().isEmpty()) {
            throw new IllegalArgumentException("icerik zorunlu.");
        }

        MedyaTipi tip = parseMedyaTipi(body.getMedyaTipi());

        Gonderi g = new Gonderi(aktif, body.getIcerik().trim(), body.getMedyaUrl(), tip);

        if (body.getMedyaBase64() != null && !body.getMedyaBase64().isBlank()) {
            String b64 = temizBase64(body.getMedyaBase64());
            if (b64.length() > MAX_BASE64_LEN) throw new MedyaCokBuyukException(b64.length());
            try { Base64.getDecoder().decode(b64); }
            catch (IllegalArgumentException e) { throw new MedyaGecersizException("base64"); }
            g.setMedyaBase64(b64);
        }

        gonderiRepository.save(g);
        return Map.of("mesaj", "Gönderi oluşturuldu.", "postId", g.getId());
    }


    @Override
    public Map<String, Object> guncelle(Kullanici aktif, Long id, GonderiOlusturIstegi body) {
        Gonderi g = findPostOr404(id);
        checkOwnerOrAdmin(g, aktif);

        if (body.getIcerik() != null && !body.getIcerik().trim().isEmpty()) {
            g.setIcerik(body.getIcerik().trim());
        }
        if (body.getMedyaUrl() != null) {
            g.setMedyaUrl(body.getMedyaUrl());
        }
        if (body.getMedyaTipi() != null && !body.getMedyaTipi().isBlank()) {
            g.setMedyaTipi(parseMedyaTipi(body.getMedyaTipi()));
        }

        if (body.getMedyaBase64() != null) {
            if (body.getMedyaBase64().isBlank()) {
                g.setMedyaBase64(null);
            } else {
                String b64 = temizBase64(body.getMedyaBase64());
                if (b64.length() > MAX_BASE64_LEN) throw new MedyaCokBuyukException(b64.length());
                try { Base64.getDecoder().decode(b64); }
                catch (IllegalArgumentException e) { throw new MedyaGecersizException("base64"); }
                g.setMedyaBase64(b64);
            }
        }

        gonderiRepository.save(g);
        return Map.of("mesaj", id + " ID'li Gönderi güncellendi.");
    }


    @Override
    public Map<String, Object> sil(Kullanici aktif, Long id) {
        Gonderi g = findPostOr404(id);
        checkOwnerOrAdmin(g, aktif);

        yorumRepository.deleteByGonderi_Id(id);
        postBegeniRepository.deleteByGonderi_Id(id);
        gonderiRepository.deleteById(id);
        return Map.of("mesaj", "Gönderi silindi.");
    }


    @Override
    public Map<String, Object> view(Long id) {
        Gonderi g = findPostOr404(id);
        g.setGoruntulenmeSayisi(g.getGoruntulenmeSayisi() + 1);
        gonderiRepository.save(g);
        return Map.of("mesaj", id+ " ID'li Gönderinin Görüntülenme sayısı arttı.", "goruntulenmeSayisi", g.getGoruntulenmeSayisi());
    }


    @Override
    public Map<String, Object> yorumEkle(Kullanici aktif, Long gonderiId, String icerik) {
        if (icerik == null || icerik.trim().isEmpty()) {
            throw new IllegalArgumentException("icerik zorunlu.");
        }
        Gonderi g = gonderiRepository.findById(gonderiId)
                .orElseThrow(() -> new YorumEkleGonderiBulunamadiException(gonderiId));

        yorumRepository.save(new Yorum(g, aktif, icerik.trim()));
        return Map.of("mesaj", "Yorum eklendi.");
    }

    @Override
    public Page<YorumGorunum> yorumlar(Long gonderiId, int page, int size) {
        return yorumRepository
                .findByGonderi_IdOrderByOlusturmaZamaniAsc(gonderiId, PageRequest.of(page, size))
                .map(YorumGorunum::new);
    }


    @Override
    public Map<String, Object> begen(Kullanici aktif, Long gonderiId) {
        findPostOr404(gonderiId);
        if (postBegeniRepository.existsByGonderi_IdAndKullanici_Id(gonderiId, aktif.getId())) {
            throw new BegeniZatenVarException(gonderiId);
        }
        postBegeniRepository.save(new PostBegeni(findPostOr404(gonderiId), aktif));
        long toplam = postBegeniRepository.countByGonderi_Id(gonderiId);
        return Map.of("mesaj", "Beğenildi.", "begeniSayisi", toplam);
    }

    @Override
    public Map<String, Object> begeniyiGeriAl(Kullanici aktif, Long gonderiId) {
        findPostOr404(gonderiId);
        boolean vardi = postBegeniRepository.existsByGonderi_IdAndKullanici_Id(gonderiId, aktif.getId());
        if (!vardi) throw new BegeniBulunamadiException(gonderiId);

        postBegeniRepository.deleteByGonderi_IdAndKullanici_Id(gonderiId, aktif.getId());
        long toplam = postBegeniRepository.countByGonderi_Id(gonderiId);
        return Map.of("mesaj", "Beğeni geri alındı.", "begeniSayisi", toplam);
    }


    private Gonderi findPostOr404(Long id) {
        return gonderiRepository.findById(id)
                .orElseThrow(() -> new GonderiBulunamadiException(id));
    }

    private void checkOwnerOrAdmin(Gonderi g, Kullanici aktif) {
        boolean admin = "ADMIN".equalsIgnoreCase(aktif.getRol());
        boolean owner = g.getKullanici().getId().equals(aktif.getId());
        if (!admin && !owner) throw new GonderiIslemYetkisizException(g.getId());
    }

    private MedyaTipi parseMedyaTipi(String tipStr) {
        if (tipStr == null || tipStr.isBlank()) return MedyaTipi.IMAGE;
        try {
            return MedyaTipi.valueOf(tipStr.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new MedyaGecersizException("type:" + tipStr);
        }
    }

    private String temizBase64(String raw) {
        String s = raw.trim();
        int idx = s.indexOf("base64,");
        if (idx >= 0) return s.substring(idx + "base64,".length());
        return s;
    }
}
