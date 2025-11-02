package com.cerensahin.sosyalmedya.dto;

import com.cerensahin.sosyalmedya.entity.Gonderi;
import java.time.LocalDateTime;

public class GonderiGorunum { 
    private Long id;
    private Long kullaniciId;
    private String icerik;
    private String medyaUrl;
    private String medyaTipi; 
    private String medyaBase64; 
    private LocalDateTime olusturmaZamani;

    public GonderiGorunum() { }

    public GonderiGorunum(Gonderi g) {
        this.id = g.getId();
        this.kullaniciId = g.getKullanici().getId();
        this.icerik = g.getIcerik();
        this.medyaUrl = g.getMedyaUrl();
        this.medyaTipi = g.getMedyaTipi().name();
        this.medyaBase64 = g.getMedyaBase64();
        this.olusturmaZamani = g.getOlusturmaZamani();

    }

    public Long getId() { return id; }
    public Long getKullaniciId() { return kullaniciId; }
    public String getIcerik() { return icerik; }
    public String getMedyaUrl() { return medyaUrl; }
    public String getMedyaTipi() { return medyaTipi; }
    public String getMedyaBase64() { return medyaBase64; }
    public LocalDateTime getOlusturmaZamani() { return olusturmaZamani; }
}
