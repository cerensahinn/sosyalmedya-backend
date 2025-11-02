package com.cerensahin.sosyalmedya.dto;

import com.cerensahin.sosyalmedya.entity.Kullanici;

public class KullaniciGorunum {
    private Long id;
    private String ad;
    private String soyad;
    private String kullaniciAdi;
    private String email;
    private String rol; // DTO'da String olarak kalabilir (JSON uyumu için)

    public KullaniciGorunum(Kullanici k) {
        this.id = k.getId();
        this.ad = k.getAd();
        this.soyad = k.getSoyad();
        this.kullaniciAdi = k.getKullaniciAdi();
        this.email = k.getEmail();
        this.rol = k.getRol().name(); // ✅ ENUM'u String'e çevir
    }

    // Getter'lar
    public Long getId() { return id; }
    public String getAd() { return ad; }
    public String getSoyad() { return soyad; }
    public String getKullaniciAdi() { return kullaniciAdi; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
}
