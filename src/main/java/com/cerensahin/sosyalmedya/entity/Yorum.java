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
    @JoinColumn(name = "gonderi_id", nullable = false)
    private Gonderi gonderi;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanici kullanici;

    @Column(nullable = false, length = 1000)
    private String icerik;

    @Column(nullable = false)
    private LocalDateTime olusturmaZamani = LocalDateTime.now();

    protected Yorum() { }

    public Yorum(Gonderi gonderi, Kullanici kullanici, String icerik) {
        this.gonderi = gonderi;
        this.kullanici = kullanici;
        this.icerik = icerik;
    }

    public Long getId() { return id; }
    public Gonderi getGonderi() { return gonderi; }
    public Kullanici getKullanici() { return kullanici; }
    public String getIcerik() { return icerik; }
    public LocalDateTime getOlusturmaZamani() { return olusturmaZamani; }
}
