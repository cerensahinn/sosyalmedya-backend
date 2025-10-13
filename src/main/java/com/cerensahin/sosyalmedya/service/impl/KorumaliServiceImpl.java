package com.cerensahin.sosyalmedya.service.impl;

import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.service.KorumaliService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KorumaliServiceImpl implements KorumaliService {

    @Override
    public Map<String, Object> ping(Kullanici aktif) {
        return Map.of(
                "mesaj", "Koruma arkasındasın 👏",
                "kullaniciId", aktif.getId(),
                "email", aktif.getEmail(),
                "rol", aktif.getRol()
        );
    }
}
