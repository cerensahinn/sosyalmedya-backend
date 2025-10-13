package com.cerensahin.sosyalmedya.ortak.exception.yorum;

public class YorumSilmeYetkiYokException extends RuntimeException {
    private final Long yorumId;

    public YorumSilmeYetkiYokException(Long yorumId) {
        super("Bu yorumu silme yetkiniz yok");
        this.yorumId = yorumId;
    }

    public Long getYorumId() {
        return yorumId;
    }
}
