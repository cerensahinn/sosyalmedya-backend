package com.cerensahin.sosyalmedya.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "erisim_tokenleri")
public class ErisimTokeni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullanici;

    @Column(nullable = false, unique = true, length = 120)
    private String deger;

    @Column(name = "olusturma_zamani", nullable = false, updatable = false)
    private LocalDateTime olusturmaZamani = LocalDateTime.now();

    @Column(name = "son_kullanma_zamani", nullable = false)
    private LocalDateTime sonKullanmaZamani;

    @Column(nullable = false)
    private boolean aktif = true;

    public ErisimTokeni() {}

    public ErisimTokeni(Kullanici kullanici, String deger, LocalDateTime olusturmaZamani, LocalDateTime sonKullanmaZamani) {
        this.kullanici = kullanici;
        this.deger = deger;
        this.olusturmaZamani = olusturmaZamani != null ? olusturmaZamani : LocalDateTime.now();
        this.sonKullanmaZamani = sonKullanmaZamani;
        this.aktif = true;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public Kullanici getKullanici() { return kullanici; }
    public String getDeger() { return deger; }
    public LocalDateTime getOlusturmaZamani() { return olusturmaZamani; }
    public LocalDateTime getSonKullanmaZamani() { return sonKullanmaZamani; }
    public boolean isAktif() { return aktif; }
    public void setAktif(boolean aktif) { this.aktif = aktif; }
}
