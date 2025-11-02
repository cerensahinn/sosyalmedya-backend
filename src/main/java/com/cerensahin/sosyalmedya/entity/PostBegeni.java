package com.cerensahin.sosyalmedya.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "post_begenileri",
        uniqueConstraints = @UniqueConstraint(columnNames = {"gonderi_id", "kullanici_id"})
)
public class PostBegeni {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "gonderi_id", nullable = false)
    private Gonderi gonderi;
//optional = false → JPA düzeyinde iş kuralı (model seviyesi)
//nullable = false → Veritabanı düzeyinde veri bütünlüğü (constraint)
    //“Bu ilişki olmadan beğeni oluşturulamaz.” demektir. yani java da gonderi alanı null olamaz
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "kullanici_id", nullable = false)
    private Kullanici kullanici;

    protected PostBegeni() { }

    public PostBegeni(Gonderi gonderi, Kullanici kullanici) {
        this.gonderi = gonderi;
        this.kullanici = kullanici;
    }

    public Long getId() { return id; }
    public Gonderi getGonderi() { return gonderi; }
    public Kullanici getKullanici() { return kullanici; }
}

