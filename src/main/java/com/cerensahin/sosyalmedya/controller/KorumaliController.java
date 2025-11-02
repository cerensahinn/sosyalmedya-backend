package com.cerensahin.sosyalmedya.controller;

import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.service.KorumaliService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/protected")
public class KorumaliController {

    private final KorumaliService korumaliService;

    public KorumaliController(KorumaliService korumaliService) {
        this.korumaliService = korumaliService;
    }

    @GetMapping("/ping")

    public Map<String, Object> ping(@RequestAttribute("aktifKullanici") Kullanici aktif) {

        var x = 5;
        return korumaliService.ping(aktif);

    }
}
