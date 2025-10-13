package com.cerensahin.sosyalmedya.service;

import com.cerensahin.sosyalmedya.entity.Kullanici;

import java.util.Map;

public interface YorumService {

    Map<String, Object> ekle(Kullanici aktif, Long gonderiId, String icerik);

    Map<String, Object> sil(Kullanici aktif, Long yorumId);
}
