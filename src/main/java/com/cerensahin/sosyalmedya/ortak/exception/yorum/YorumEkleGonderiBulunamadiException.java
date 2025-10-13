package com.cerensahin.sosyalmedya.ortak.exception.yorum;

public class YorumEkleGonderiBulunamadiException extends RuntimeException {
    private final Long gonderiId;

    public YorumEkleGonderiBulunamadiException(Long gonderiId) {
        super("Yorum eklenemedi: Gönderi bulunamadı.");
        this.gonderiId = gonderiId;
    }

    public Long getGonderiId() {
        return gonderiId;
    }
}
