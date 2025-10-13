package com.cerensahin.sosyalmedya.ortak.exception.gonderi;

public class BegeniZatenVarException extends RuntimeException {
    private final Long gonderiId;
    public BegeniZatenVarException(Long gonderiId) {
        super("Bu gönderi zaten beğenilmiş");
        this.gonderiId = gonderiId;
    }
    public Long getGonderiId() { return gonderiId; }
}
