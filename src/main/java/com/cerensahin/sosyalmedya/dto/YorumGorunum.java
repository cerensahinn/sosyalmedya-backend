package com.cerensahin.sosyalmedya.dto;

import com.cerensahin.sosyalmedya.entity.Yorum;
import java.time.LocalDateTime;

public class YorumGorunum {
    private Long id;
    private Long kullaniciId;
    private String icerik;
    private LocalDateTime olusturmaZamani;

    public YorumGorunum() { }

    public YorumGorunum(Yorum y) {
        this.id = y.getId();
        this.kullaniciId = y.getKullanici().getId();
        this.icerik = y.getIcerik();
        this.olusturmaZamani = y.getOlusturmaZamani();
    }

    public Long getId() { return id; }
    public Long getKullaniciId() { return kullaniciId; }
    public String getIcerik() { return icerik; }
    public LocalDateTime getOlusturmaZamani() { return olusturmaZamani; }
}
