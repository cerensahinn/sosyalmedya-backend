package com.cerensahin.sosyalmedya.ortak.exception.gonderi;

public class GonderiBulunamadiException extends RuntimeException {
    private final Long gonderiId;
    public GonderiBulunamadiException(Long gonderiId) {
        super("Gönderi bulunamadı");
        this.gonderiId = gonderiId;
    }
    public Long getGonderiId() { return gonderiId; }
}
