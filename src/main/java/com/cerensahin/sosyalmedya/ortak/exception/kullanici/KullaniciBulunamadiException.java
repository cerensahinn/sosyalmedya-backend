package com.cerensahin.sosyalmedya.ortak.exception.kullanici;

public class KullaniciBulunamadiException extends RuntimeException {
    private final Long kullaniciId;
    public KullaniciBulunamadiException(Long kullaniciId) {
        super("Kullanıcı bulunamadı");
        this.kullaniciId = kullaniciId;
    }
    public Long getKullaniciId() { return kullaniciId; }
}
