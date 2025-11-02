package com.cerensahin.sosyalmedya.dto;

public class GirisIstegi { //Login Request
    private final String email;
    private final String sifre;

    public GirisIstegi(String email, String sifre) {
        this.email = email;
        this.sifre = sifre;
    }

    public String getEmail()
    { return email; }
    public String getSifre()
    { return sifre; }
}

