package com.cerensahin.sosyalmedya.service;

import com.cerensahin.sosyalmedya.dto.GirisIstegi;
import com.cerensahin.sosyalmedya.dto.GirisYaniti;

import java.util.Map;

public interface KimlikService {

    Map<String, Object> kayit(Map<String, String> veri);


    GirisYaniti giris(GirisIstegi istek);
    //“Giriş yap” işini tanımlar. Girdi: e-posta ve şifre içeren GirisIstegi.
    // Çıktı: GirisYaniti (mesaj + token + son kullanma).

    Map<String, Object> cikis(String authorizationHeader);
    //Çıkış yap” işini tanımlar. Girdi: HTTP Authorization başlığı (ör: Token abc123). Çıktı: kısa bir mesaj içeren Map.
}
