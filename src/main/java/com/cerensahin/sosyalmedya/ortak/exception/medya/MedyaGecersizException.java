package com.cerensahin.sosyalmedya.ortak.exception.medya;

public class MedyaGecersizException extends RuntimeException {
    private final String contentType;
    public MedyaGecersizException(String contentType) {
        super("Medya geçersiz veya desteklenmiyor");
        this.contentType = contentType;
    }
    public String getContentType() { return contentType; }
}
