package com.cerensahin.sosyalmedya.service.impl;

import com.cerensahin.sosyalmedya.entity.Gonderi;
import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.entity.Yorum;
import com.cerensahin.sosyalmedya.ortak.exception.yorum.YorumBulunamadiException;
import com.cerensahin.sosyalmedya.ortak.exception.yorum.YorumEkleGonderiBulunamadiException;
import com.cerensahin.sosyalmedya.ortak.exception.yorum.YorumSilmeYetkiYokException;
import com.cerensahin.sosyalmedya.repository.GonderiRepository;
import com.cerensahin.sosyalmedya.repository.YorumRepository;
import com.cerensahin.sosyalmedya.service.YorumService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class YorumServiceImpl implements YorumService {

    private final YorumRepository yorumRepository;
    private final GonderiRepository gonderiRepository;

    public YorumServiceImpl(YorumRepository yorumRepository,
                            GonderiRepository gonderiRepository) {
        this.yorumRepository = yorumRepository;
        this.gonderiRepository = gonderiRepository;
    }

    @Override
    @Transactional
    public Map<String, Object> ekle(Kullanici aktif, Long gonderiId, String icerik) {
        if (icerik == null || icerik.trim().isEmpty()) {
            throw new IllegalArgumentException("icerik zorunlu.");
        }
        Gonderi g = gonderiRepository.findById(gonderiId)
                .orElseThrow(() -> new YorumEkleGonderiBulunamadiException(gonderiId));

        Yorum kayit = new Yorum(g, aktif, icerik.trim());
        yorumRepository.save(kayit);

        return Map.of("mesaj", "Yorum eklendi.", "yorumId", kayit.getId());
    }

    @Override
    @Transactional
    public Map<String, Object> sil(Kullanici aktif, Long yorumId) {
        Yorum y = yorumRepository.findById(yorumId)
                .orElseThrow(() -> new YorumBulunamadiException(yorumId));

        boolean admin = "ADMIN".equalsIgnoreCase(aktif.getRol());
        boolean yorumSahibi = y.getKullanici().getId().equals(aktif.getId());
        boolean postSahibi  = y.getGonderi().getKullanici().getId().equals(aktif.getId());

        if (!(admin || yorumSahibi || postSahibi)) {
            throw new YorumSilmeYetkiYokException(yorumId);
        }

        yorumRepository.deleteById(yorumId);
        return Map.of("mesaj", "Yorum silindi.");
    }
}
