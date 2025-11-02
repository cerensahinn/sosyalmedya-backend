package com.cerensahin.sosyalmedya.service;

import java.security.SecureRandom; 

import java.util.Base64;

public class TokenUretici {
    private static final SecureRandom RASTGELE = new SecureRandom();

    public static String uret(int baytUzunlugu)

    {
        byte[] buf = new byte[baytUzunlugu];

        RASTGELE.nextBytes(buf);

        return Base64.getUrlEncoder().withoutPadding().encodeToString(buf);

    }
}
