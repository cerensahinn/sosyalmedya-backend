package com.cerensahin.sosyalmedya.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "erisim_tokenleri")
public class ErisimTokeni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanici kullanici;

    @Column(nullable = false, unique = true)
    private String deger;

    @Column(nullable = false)
    private LocalDateTime olusturmaZamani;
    @Column(nullable = false)
    private LocalDateTime sonKullanmaZamani;

    @Column(nullable = false)
    private boolean aktif = true;

    public ErisimTokeni() { }

    public ErisimTokeni(Kullanici kullanici, String deger,
                        LocalDateTime olusturmaZamani, LocalDateTime sonKullanmaZamani) {
        this.kullanici = kullanici;
        this.deger = deger;
        this.olusturmaZamani = olusturmaZamani;
        this.sonKullanmaZamani = sonKullanmaZamani;
        this.aktif = true;
    }

    public Long getId() { return id; }

    public Kullanici getKullanici() { return kullanici; }
    public void setKullanici(Kullanici kullanici) { this.kullanici = kullanici; }

    public String getDeger() { return deger; }
    public void setDeger(String deger) { this.deger = deger; }

    public LocalDateTime getOlusturmaZamani() { return olusturmaZamani; }
    public void setOlusturmaZamani(LocalDateTime olusturmaZamani) { this.olusturmaZamani = olusturmaZamani; }

    public LocalDateTime getSonKullanmaZamani() { return sonKullanmaZamani; }
    public void setSonKullanmaZamani(LocalDateTime sonKullanmaZamani) { this.sonKullanmaZamani = sonKullanmaZamani; }

    public boolean isAktif() { return aktif; }
    public void setAktif(boolean aktif) { this.aktif = aktif; }
}
