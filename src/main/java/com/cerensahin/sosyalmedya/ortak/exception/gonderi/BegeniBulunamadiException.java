package com.cerensahin.sosyalmedya.ortak.exception.gonderi;

public class BegeniBulunamadiException extends RuntimeException {
    private final Long gonderiId;
    public BegeniBulunamadiException(Long gonderiId) {
        super("Beğeni bulunamadı");
        this.gonderiId = gonderiId;
    }
    public Long getGonderiId() { return gonderiId; }
}
