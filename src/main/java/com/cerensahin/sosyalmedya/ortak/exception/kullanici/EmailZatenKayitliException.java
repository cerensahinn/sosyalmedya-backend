package com.cerensahin.sosyalmedya.ortak.exception.kullanici;

public class EmailZatenKayitliException extends RuntimeException {
    private final String email;
    public EmailZatenKayitliException(String email) {
        super("Bu e-posta adresi zaten kayıtlı");
        this.email = email;
    }
    public String getEmail() { return email; }
}
