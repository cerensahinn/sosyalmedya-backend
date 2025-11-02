package com.cerensahin.sosyalmedya.service.impl;

import com.cerensahin.sosyalmedya.dto.GirisIstegi;
import com.cerensahin.sosyalmedya.dto.GirisYaniti;
import com.cerensahin.sosyalmedya.entity.ErisimTokeni;
import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.entity.Rol;
import com.cerensahin.sosyalmedya.ortak.SifreGizleme;
import com.cerensahin.sosyalmedya.ortak.exception.kimlik.GecersizKimlikBilgileriException;
import com.cerensahin.sosyalmedya.ortak.exception.kimlik.TokenGecersizException;
import com.cerensahin.sosyalmedya.ortak.exception.kullanici.EmailZatenKayitliException;
import com.cerensahin.sosyalmedya.repository.ErisimTokeniRepository;
import com.cerensahin.sosyalmedya.repository.KullaniciRepository;
import com.cerensahin.sosyalmedya.service.KimlikService;
import com.cerensahin.sosyalmedya.service.TokenUretici;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class KimlikServiceImpl implements KimlikService {

    private final KullaniciRepository kullaniciRepository;
    private final ErisimTokeniRepository erisimTokeniRepository;

    // 🔧 Token format sabitleri
    private static final String AUTH_HEADER_PREFIX = "Token ";
    private static final int MIN_TOKEN_LEN = 40;                 // Base64 URL-safe 32 byte ≈ 43-44 char
    private static final String ALLOWED_TOKEN_REGEX = "^[A-Za-z0-9_-]+$";
    // İsteğe bağlı zorunlu token öneki (kurumsal kullanım için). Gerekirse aç:
    // private static final String REQUIRED_TOKEN_PREFIX = "TK_";

    public KimlikServiceImpl(KullaniciRepository kullaniciRepository,
                             ErisimTokeniRepository erisimTokeniRepository) {
        this.kullaniciRepository = kullaniciRepository;
        this.erisimTokeniRepository = erisimTokeniRepository;
    }

    @Override
    @Transactional
    public Map<String, Object> kayit(Map<String, String> veri) {
        String ad           = veri.get("ad");
        String soyad        = veri.get("soyad");
        String kullaniciAdi = veri.get("kullaniciAdi");
        String email        = veri.get("email");
        String sifre        = veri.get("sifre");

        if (ad == null || soyad == null || email == null || sifre == null || kullaniciAdi == null) {
            throw new IllegalArgumentException("ad, soyad, kullaniciAdi, email ve sifre zorunludur.");
        }

        ad = ad.trim();
        soyad = soyad.trim();
        kullaniciAdi = kullaniciAdi.trim();
        email = email.trim().toLowerCase();

        if (ad.isBlank() || soyad.isBlank() || kullaniciAdi.isBlank() || email.isBlank() || sifre.isBlank()) {
            throw new IllegalArgumentException("Alanlar boş bırakılamaz.");
        }

        if (kullaniciRepository.existsByKullaniciAdi(kullaniciAdi)) {
            throw new IllegalArgumentException("Bu kullanıcı adı zaten kayıtlı!");
        }
        if (kullaniciRepository.existsByEmail(email)) {
            throw new EmailZatenKayitliException(email);
        }

        String sifreHash = SifreGizleme.sha256(sifre);

        Kullanici yeni = new Kullanici();
        yeni.setAd(ad);
        yeni.setSoyad(soyad);
        yeni.setKullaniciAdi(kullaniciAdi);
        yeni.setEmail(email);
        yeni.setSifreHash(sifreHash);
        yeni.setRol(Rol.USER);

        kullaniciRepository.save(yeni);

        return Map.of(
                "mesaj", "Kayıt başarılı",
                "kullaniciId", yeni.getId(),
                "kullaniciAdi", yeni.getKullaniciAdi()
        );
    }

    @Override
    @Transactional
    public GirisYaniti giris(GirisIstegi istek) {
        String email = istek.getEmail() == null ? null : istek.getEmail().trim().toLowerCase();
        String sifre = istek.getSifre();

        if (email == null || sifre == null) {
            throw new GecersizKimlikBilgileriException();
        }

        Kullanici kullanici = kullaniciRepository.findByEmail(email)
                .orElseThrow(GecersizKimlikBilgileriException::new);

        String girilenHash = SifreGizleme.sha256(sifre);
        if (!girilenHash.equals(kullanici.getSifreHash())) {
            throw new GecersizKimlikBilgileriException();
        }

        String tokenDegeri = TokenUretici.uret(32);
        LocalDateTime simdi = LocalDateTime.now();
        LocalDateTime son = simdi.plusHours(24);

        ErisimTokeni kayit = new ErisimTokeni(kullanici, tokenDegeri, simdi, son);
        erisimTokeniRepository.save(kayit);

        return new GirisYaniti("Giriş başarılı", tokenDegeri, son.toString());
    }

    @Override
    @Transactional
    public Map<String, Object> cikis(String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith(AUTH_HEADER_PREFIX)) {
            throw new TokenGecersizException(null);
        }


        String deger = authorizationHeader.substring(AUTH_HEADER_PREFIX.length()).trim();

        validateTokenFormatOrThrow(deger);

        ErisimTokeni token = erisimTokeniRepository.findByDegerAndAktifTrue(deger)
                .orElseThrow(() -> new TokenGecersizException(deger));

        token.setAktif(false);
        erisimTokeniRepository.save(token);

        return Map.of("mesaj", "Çıkış yapıldı (token iptal edildi).");
    }

    private void validateTokenFormatOrThrow(String token) {
        if (token == null || token.isEmpty()) {
            throw new TokenGecersizException("BOŞ TOKEN");
        }
        if (token.length() < MIN_TOKEN_LEN) {
            throw new TokenGecersizException("Token çok kısa: " + token.length() + " karakter.");
        }
        if (!token.matches(ALLOWED_TOKEN_REGEX)) {
            throw new TokenGecersizException("Token geçersiz karakter içeriyor.");
        }

    }
}
