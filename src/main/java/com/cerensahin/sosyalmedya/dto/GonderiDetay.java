package com.cerensahin.sosyalmedya.dto;

import com.cerensahin.sosyalmedya.entity.Gonderi;
import java.time.LocalDateTime;
import java.util.List;

public class GonderiDetay {
    private Long id;
    private Long kullaniciId;
    private String icerik;
    private String medyaUrl;
    private String medyaTipi;
    private String medyaBase64;
    private LocalDateTime olusturmaZamani;
    private long goruntulenmeSayisi;
    private long begeniSayisi;
    private long yorumSayisi;
    private List<YorumGorunum> yorumlar;

    public GonderiDetay() {}

    public GonderiDetay(Gonderi g, long begeni, long yorumSayisi, List<YorumGorunum> yorumlar) {
        this.id = g.getId();
        this.kullaniciId = g.getKullanici().getId();
        this.icerik = g.getIcerik();
        this.medyaUrl = g.getMedyaUrl();
        this.medyaTipi = g.getMedyaTipi().name();
        this.medyaBase64 = g.getMedyaBase64();
        this.olusturmaZamani = g.getOlusturmaZamani();
        this.goruntulenmeSayisi = g.getGoruntulenmeSayisi(); 
        this.begeniSayisi = begeni;
        this.yorumSayisi = yorumSayisi;
        this.yorumlar = yorumlar;
    }

    public Long getId() { return id; }
    public Long getKullaniciId() { return kullaniciId; }
    public String getIcerik() { return icerik; }
    public String getMedyaUrl() { return medyaUrl; }
    public String getMedyaTipi() { return medyaTipi; }
    public String getMedyaBase64() { return medyaBase64; }
    public LocalDateTime getOlusturmaZamani() { return olusturmaZamani; }
    public long getGoruntulenmeSayisi() { return goruntulenmeSayisi; }
    public long getBegeniSayisi() { return begeniSayisi; }
    public long getYorumSayisi() { return yorumSayisi; }
    public List<YorumGorunum> getYorumlar() { return yorumlar; }
}
