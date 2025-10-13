package com.cerensahin.sosyalmedya.service;

import com.cerensahin.sosyalmedya.entity.Kullanici;

import java.util.Map;

public interface KorumaliService {

    Map<String, Object> ping(Kullanici aktif);
}
