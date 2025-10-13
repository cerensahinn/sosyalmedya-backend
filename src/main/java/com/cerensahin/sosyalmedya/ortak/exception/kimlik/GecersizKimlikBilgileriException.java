package com.cerensahin.sosyalmedya.ortak.exception.kimlik;

public class GecersizKimlikBilgileriException extends RuntimeException {
    public GecersizKimlikBilgileriException() {
        super("Geçersiz e-posta veya şifre");
    }
}
