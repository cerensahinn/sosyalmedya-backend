package com.cerensahin.sosyalmedya.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "yorumlar")
public class Yorum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "gonderi_id")
    private Gonderi gonderi;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id")
    private Kullanici kullanici;

    @Column(nullable = false, length = 500)
    private String icerik;

    @Column(name = "olusturma_zamani", nullable = false, updatable = false)
    private LocalDateTime olusturmaZamani = LocalDateTime.now();

    public Yorum() {}

    public Yorum(Gonderi gonderi, Kullanici kullanici, String icerik) {
        this.gonderi = gonderi;
        this.kullanici = kullanici;
        this.icerik = icerik;
        this.olusturmaZamani = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public Gonderi getGonderi() { return gonderi; }
    public Kullanici getKullanici() { return kullanici; }
    public String getIcerik() { return icerik; }
    public LocalDateTime getOlusturmaZamani() { return olusturmaZamani; }
}
