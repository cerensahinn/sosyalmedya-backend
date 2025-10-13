package com.cerensahin.sosyalmedya.dto;

import com.cerensahin.sosyalmedya.entity.Kullanici;

import java.time.LocalDateTime;

public class KullaniciGorunum {
    private Long id;
    private String ad;
    private String soyad;
    private String email;
    private String rol;
    private LocalDateTime olusturmaZamani;

    public KullaniciGorunum() { }

    public KullaniciGorunum(Kullanici k) {
        this.id = k.getId();
        this.ad = k.getAd();
        this.soyad = k.getSoyad();
        this.email = k.getEmail();
        this.rol = k.getRol();
        this.olusturmaZamani = k.getOlusturmaZamani();
    }

    public Long getId() { return id; }
    public String getAd() { return ad; }
    public String getSoyad() { return soyad; }
    public String getEmail() { return email; }
    public String getRol() { return rol; }
    public LocalDateTime getOlusturmaZamani() { return olusturmaZamani; }
}
