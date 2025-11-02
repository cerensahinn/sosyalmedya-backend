package com.cerensahin.sosyalmedya.service;

import com.cerensahin.sosyalmedya.dto.GirisIstegi;
import com.cerensahin.sosyalmedya.dto.GirisYaniti;

import java.util.Map;

public interface KimlikService {

    Map<String, Object> kayit(Map<String, String> veri);

    GirisYaniti giris(GirisIstegi istek);

    Map<String, Object> cikis(String authorizationHeader);

}
