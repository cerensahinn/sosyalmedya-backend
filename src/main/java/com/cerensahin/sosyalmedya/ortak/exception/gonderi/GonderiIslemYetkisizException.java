package com.cerensahin.sosyalmedya.ortak.exception.gonderi;

public class GonderiIslemYetkisizException extends RuntimeException {
    private final Long gonderiId;

    public GonderiIslemYetkisizException(Long gonderiId) {
        super("Bu gönderi üzerinde işlem yetkiniz yok");
        this.gonderiId = gonderiId;
    }

    public Long getGonderiId() {
        return gonderiId;
    }
}
