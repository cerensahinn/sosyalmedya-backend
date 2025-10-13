package com.cerensahin.sosyalmedya.ortak.exception.kullanici;

public class KullaniciAdiZatenKayitliException extends RuntimeException {
    private final String kullaniciAdi;

    public KullaniciAdiZatenKayitliException(String kullaniciAdi) {
        super("Bu kullanıcı adı zaten kayıtlı!");
        this.kullaniciAdi = kullaniciAdi;
    }

    public String getKullaniciAdi() {
        return kullaniciAdi;
    }
}
