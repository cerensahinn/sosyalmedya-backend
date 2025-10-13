package com.cerensahin.sosyalmedya.service.impl;

import com.cerensahin.sosyalmedya.dto.KullaniciGorunum;
import com.cerensahin.sosyalmedya.dto.ParolaGuncelleIstegi;
import com.cerensahin.sosyalmedya.entity.ErisimTokeni;
import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.ortak.SifreGizleme;
import com.cerensahin.sosyalmedya.ortak.exception.kullanici.EskiSifreHataliException;
import com.cerensahin.sosyalmedya.ortak.exception.kullanici.KullaniciBulunamadiException;
import com.cerensahin.sosyalmedya.repository.ErisimTokeniRepository;
import com.cerensahin.sosyalmedya.repository.GonderiRepository;
import com.cerensahin.sosyalmedya.repository.KullaniciRepository;
import com.cerensahin.sosyalmedya.service.KullaniciService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class KullaniciServiceImpl implements KullaniciService {

    private final KullaniciRepository kullaniciRepository;
    private final GonderiRepository gonderiRepository;
    private final ErisimTokeniRepository erisimTokeniRepository;

    public KullaniciServiceImpl(KullaniciRepository kullaniciRepository,
                                GonderiRepository gonderiRepository,
                                ErisimTokeniRepository erisimTokeniRepository) {
        this.kullaniciRepository = kullaniciRepository;
        this.gonderiRepository = gonderiRepository;
        this.erisimTokeniRepository = erisimTokeniRepository;
    }

    @Override
    public KullaniciGorunum getById(Long id) {
        Kullanici k = kullaniciRepository.findById(id)
                .orElseThrow(() -> new KullaniciBulunamadiException(id));
        return new KullaniciGorunum(k);
    }

    @Override
    @Transactional
    public Map<String, Object> changePassword(Kullanici aktif, ParolaGuncelleIstegi body) {
        if (body == null || body.getEskiSifre() == null || body.getYeniSifre() == null) {
            throw new IllegalArgumentException("eskiSifre ve yeniSifre zorunlu.");
        }

        String eskiHash = SifreGizleme.sha256(body.getEskiSifre());
        if (!eskiHash.equals(aktif.getSifreHash())) {
            throw new EskiSifreHataliException();
        }

        String yeniHash = SifreGizleme.sha256(body.getYeniSifre());
        aktif.setSifreHash(yeniHash);
        kullaniciRepository.save(aktif);

        ErisimTokeni sonToken = erisimTokeniRepository
                .findTopByKullanici_IdAndAktifTrueOrderByOlusturmaZamaniDesc(aktif.getId())
                .orElse(null);
        assert sonToken != null;
        sonToken.setAktif(false);
        erisimTokeniRepository.save(sonToken);

        return Map.of("mesaj", "Şifre güncellendi.");
    }

    @Override
    @Transactional
    public Map<String, Object> deleteMe(Kullanici aktif) {
        Long uid = aktif.getId();
        gonderiRepository.deleteByKullanici_Id(uid);
        erisimTokeniRepository.deleteByKullanici_Id(uid);
        erisimTokeniRepository.flush();
        kullaniciRepository.deleteById(uid);
        return Map.of("mesaj", "Hesabınız silindi.");
    }

    @Override
    @Transactional
    public Map<String, Object> adminDelete(Kullanici aktif, Long id) {
        if (!"ADMIN".equalsIgnoreCase(aktif.getRol())) {
            throw new IllegalArgumentException("Bu işlem için ADMIN rolü gerekir.");
        }
        if (!kullaniciRepository.existsById(id)) {
            throw new KullaniciBulunamadiException(id);
        }
        gonderiRepository.deleteByKullanici_Id(id);
        erisimTokeniRepository.deleteByKullanici_Id(id);
        erisimTokeniRepository.flush();
        kullaniciRepository.deleteById(id);

        return Map.of("mesaj", "Kullanıcı silindi.", "silinenId", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<KullaniciGorunum> adminListAll(Kullanici aktif) {
        if (!"ADMIN".equalsIgnoreCase(aktif.getRol())) {
            throw new IllegalArgumentException("Bu işlem için ADMIN rolü gerekir.");
        }
        return kullaniciRepository.findAll().stream()
                .map(KullaniciGorunum::new)
                .toList();
    }
}
