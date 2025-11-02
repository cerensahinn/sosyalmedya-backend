package com.cerensahin.sosyalmedya.service;

import com.cerensahin.sosyalmedya.entity.Kullanici;

import java.util.Map;

public interface KorumaliService {

    Map<String, Object> ping(Kullanici aktif);
    //sistemde giriş yapmış olan bir aktif kullanıcı nesnesini alır
    //ve bir cevap döndürür (örneğin mesaj + kullanıcı bilgileri).
    //Yani bu metodun amacı:
    //“Token doğrulaması geçmiş bir kullanıcı gerçekten sisteme ulaşabiliyor mu?”
    //bunu test etmek.
}
