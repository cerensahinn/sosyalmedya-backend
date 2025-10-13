package com.cerensahin.sosyalmedya.ortak.exception.kimlik;

import java.time.LocalDateTime;

public class TokenSuresiDolmusException extends RuntimeException {
    private final LocalDateTime sonKullanim;
    public TokenSuresiDolmusException(LocalDateTime sonKullanim) {
        super("Erişim token'ının süresi dolmuş");
        this.sonKullanim = sonKullanim;
    }
    public LocalDateTime getSonKullanim() { return sonKullanim; }
}
