package com.cerensahin.sosyalmedya.ortak;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

public class SifreGizleme {
    public static String sha256(String metin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] d = md.digest(metin.getBytes(StandardCharsets.UTF_8));

            String sonuc = "";
            for (byte b : d) {
                sonuc = sonuc + String.format("%02x", b);
            }
            return sonuc;
        } catch (Exception e) {

            throw new RuntimeException("SHA-256 desteklenmiyor");
        }
    }
}
