package com.cerensahin.sosyalmedya.service;

import com.cerensahin.sosyalmedya.dto.KullaniciGorunum;
import com.cerensahin.sosyalmedya.dto.ParolaGuncelleIstegi;
import com.cerensahin.sosyalmedya.entity.Kullanici;

import java.util.List;
import java.util.Map;

public interface KullaniciService {

    KullaniciGorunum getById(Long id);

    Map<String, Object> changePassword(Kullanici aktif, ParolaGuncelleIstegi body);

    Map<String, Object> deleteMe(Kullanici aktif);

    Map<String, Object> adminDelete(Kullanici aktif, Long id);

    // 🔹 ADMIN → tüm kullanıcıları listele
    List<KullaniciGorunum> adminListAll(Kullanici aktif);
}
