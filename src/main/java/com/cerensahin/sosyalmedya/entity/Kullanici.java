package com.cerensahin.sosyalmedya.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "kullanicilar",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_kullanici_email", columnNames = "email"),
                @UniqueConstraint(name = "uk_kullanici_kullanici_adi", columnNames = "kullanici_adi")
        }
)
public class Kullanici {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ad;

    @Column(nullable = false)
    private String soyad;

    @Column(name = "kullanici_adi", nullable = false, unique = true, length = 50)
    private String kullaniciAdi;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String sifreHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Rol rol = Rol.USER;

    @Column(name = "olusturma_zamani", nullable = false, updatable = false)
    private LocalDateTime olusturmaZamani = LocalDateTime.now();

    public Kullanici() {}

    public Kullanici(String ad, String soyad, String kullaniciAdi, String email, String sifreHash, Rol rol) {
        this.ad = ad;
        this.soyad = soyad;
        this.kullaniciAdi = kullaniciAdi;
        this.email = email;
        this.sifreHash = sifreHash;
        this.rol = rol;
        this.olusturmaZamani = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getAd() { return ad; }
    public void setAd(String ad) { this.ad = ad; }
    public String getSoyad() { return soyad; }
    public void setSoyad(String soyad) { this.soyad = soyad; }
    public String getKullaniciAdi() { return kullaniciAdi; }
    public void setKullaniciAdi(String kullaniciAdi) { this.kullaniciAdi = kullaniciAdi; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSifreHash() { return sifreHash; }
    public void setSifreHash(String sifreHash) { this.sifreHash = sifreHash; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public LocalDateTime getOlusturmaZamani() { return olusturmaZamani; }
}
