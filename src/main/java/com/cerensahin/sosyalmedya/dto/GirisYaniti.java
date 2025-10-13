package com.cerensahin.sosyalmedya.dto;

public class GirisYaniti {
    private final String mesaj;
    private final String token;
    private final String sonKullanmaZamaniIso;

    public GirisYaniti(String mesaj, String token, String sonKullanmaZamaniIso) {
        this.mesaj = mesaj;
        this.token = token;
        this.sonKullanmaZamaniIso = sonKullanmaZamaniIso;
    }

    public String getMesaj() { return mesaj; }
    public String getToken() { return token; }
    public String getSonKullanmaZamaniIso()
    { return sonKullanmaZamaniIso; }
}
