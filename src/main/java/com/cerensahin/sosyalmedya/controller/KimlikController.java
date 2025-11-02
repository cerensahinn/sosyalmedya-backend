package com.cerensahin.sosyalmedya.controller;

import com.cerensahin.sosyalmedya.dto.GirisIstegi;
import com.cerensahin.sosyalmedya.dto.GirisYaniti;
import com.cerensahin.sosyalmedya.service.KimlikService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class KimlikController {

    private final KimlikService kimlikService;

    public KimlikController(KimlikService kimlikService) {
        this.kimlikService = kimlikService;
    }

    @PostMapping("/signup")
    public Map<String, Object> kayit(@RequestBody Map<String, String> veri) {
        return kimlikService.kayit(veri);
    }

    @PostMapping("/login")
    public ResponseEntity<GirisYaniti> giris(@RequestBody GirisIstegi istek) {
        return ResponseEntity.ok(kimlikService.giris(istek));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, Object>> cikis(
            @RequestHeader(value = "Authorization", required = true) String authorization // Artık zorunlu
    ) {
        return ResponseEntity.ok(kimlikService.cikis(authorization));
    }
}
