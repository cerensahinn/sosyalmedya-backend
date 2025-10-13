package com.cerensahin.sosyalmedya.service;

import com.cerensahin.sosyalmedya.dto.GonderiDetay;
import com.cerensahin.sosyalmedya.dto.GonderiGorunum;
import com.cerensahin.sosyalmedya.dto.GonderiOlusturIstegi;
import com.cerensahin.sosyalmedya.dto.YorumGorunum;
import com.cerensahin.sosyalmedya.entity.Kullanici;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface GonderiService {
    Page<GonderiGorunum> liste(int page, int size);
    Page<GonderiGorunum> kullaniciGonderileri(Long kullaniciId, int page, int size);
    GonderiDetay detay(Long id, int page, int size);

    Map<String, Object> olustur(Kullanici aktif, GonderiOlusturIstegi body);
    Map<String, Object> guncelle(Kullanici aktif, Long id, GonderiOlusturIstegi body);
    Map<String, Object> sil(Kullanici aktif, Long id);

    Map<String, Object> view(Long id);

    Map<String, Object> yorumEkle(Kullanici aktif, Long gonderiId, String icerik);
    Page<YorumGorunum> yorumlar(Long gonderiId, int page, int size);

    Map<String, Object> begen(Kullanici aktif, Long gonderiId);
    Map<String, Object> begeniyiGeriAl(Kullanici aktif, Long gonderiId);
}
