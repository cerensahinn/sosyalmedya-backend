package com.cerensahin.sosyalmedya.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gonderiler")
public class Gonderi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullanici;

    @Column(nullable = false, length = 2200)
    private String icerik;

    @Column(length = 2048)
    private String medyaUrl;

    @Lob
    @Column(name = "medya_base64", columnDefinition = "text")
    private String medyaBase64;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private MedyaTipi medyaTipi = MedyaTipi.IMAGE;

    @Column(name = "goruntulenme_sayisi", nullable = false)
    private long goruntulenmeSayisi = 0;

    @Column(name = "olusturma_zamani", nullable = false, updatable = false)
    private LocalDateTime olusturmaZamani = LocalDateTime.now();

    public Gonderi() {}

    public Gonderi(Kullanici kullanici, String icerik, String medyaUrl, MedyaTipi medyaTipi) {
        this.kullanici = kullanici;
        this.icerik = icerik;
        this.medyaUrl = medyaUrl;
        this.medyaTipi = medyaTipi;
        this.olusturmaZamani = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Kullanici getKullanici() { return kullanici; }
    public void setKullanici(Kullanici kullanici) { this.kullanici = kullanici; }
    public String getIcerik() { return icerik; }
    public void setIcerik(String icerik) { this.icerik = icerik; }
    public String getMedyaUrl() { return medyaUrl; }
    public void setMedyaUrl(String medyaUrl) { this.medyaUrl = medyaUrl; }
    public String getMedyaBase64() { return medyaBase64; }
    public void setMedyaBase64(String medyaBase64) { this.medyaBase64 = medyaBase64; }
    public MedyaTipi getMedyaTipi() { return medyaTipi; }
    public void setMedyaTipi(MedyaTipi medyaTipi) { this.medyaTipi = medyaTipi; }
    public long getGoruntulenmeSayisi() { return goruntulenmeSayisi; }
    public void setGoruntulenmeSayisi(long goruntulenmeSayisi) { this.goruntulenmeSayisi = goruntulenmeSayisi; }
    public LocalDateTime getOlusturmaZamani() { return olusturmaZamani; }
}
