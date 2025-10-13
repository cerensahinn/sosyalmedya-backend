package com.cerensahin.sosyalmedya.controller;

import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.service.YorumService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class YorumController {

    private final YorumService yorumService;

    public YorumController(YorumService yorumService) {
        this.yorumService = yorumService;
    }

    @PostMapping
    public Map<String, Object> ekle(@RequestAttribute("aktifKullanici") Kullanici aktif,
                                    @RequestBody Map<String, String> body) {

        String icerik = body.get("icerik");
        String gonderiIdStr = body.get("gonderiId");

        if (icerik == null || icerik.trim().isEmpty()) {
            throw new IllegalArgumentException("icerik zorunlu.");
        }
        if (gonderiIdStr == null || gonderiIdStr.isBlank()) {
            throw new IllegalArgumentException("gonderiId zorunlu.");
        }

        Long gonderiId;
        try {
            gonderiId = Long.parseLong(gonderiIdStr.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("gonderiId sayısal olmalıdır.");
        }

        return yorumService.ekle(aktif, gonderiId, icerik.trim());
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> sil(@RequestAttribute("aktifKullanici") Kullanici aktif,
                                   @PathVariable Long id) {
        return yorumService.sil(aktif, id);
    }
}
