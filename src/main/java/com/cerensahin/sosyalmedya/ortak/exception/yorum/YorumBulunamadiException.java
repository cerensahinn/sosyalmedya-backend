package com.cerensahin.sosyalmedya.ortak.exception.yorum;

public class YorumBulunamadiException extends RuntimeException {
    private final Long yorumId;

    public YorumBulunamadiException(Long yorumId) {
        super("Yorum bulunamadı");
        this.yorumId = yorumId;
    }

    public Long getYorumId() {
        return yorumId;
    }
}
