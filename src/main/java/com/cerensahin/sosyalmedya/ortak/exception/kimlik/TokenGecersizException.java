package com.cerensahin.sosyalmedya.ortak.exception.kimlik;

public class TokenGecersizException extends RuntimeException {
    private final String token;
    public TokenGecersizException(String token) {
        super("Erişim token'ı geçersiz");
        this.token = token;
    }
    public String getToken() { return token; }
}
