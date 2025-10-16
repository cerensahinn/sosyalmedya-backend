package com.cerensahin.sosyalmedya.controller;

import com.cerensahin.sosyalmedya.dto.KullaniciGorunum;
import com.cerensahin.sosyalmedya.dto.ParolaGuncelleIstegi;
import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.service.KullaniciService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class KullaniciController {

    private final KullaniciService kullaniciService;

    public KullaniciController(KullaniciService kullaniciService) {
        this.kullaniciService = kullaniciService;
    }

    @GetMapping("/users/{id}")
    public KullaniciGorunum getById(@PathVariable Long id) {
        return kullaniciService.getById(id);
    }

    @PutMapping("/users/me/password")
    public Map<String, Object> changePassword(
            @RequestAttribute("aktifKullanici") Kullanici aktif,
            @RequestBody ParolaGuncelleIstegi body) {
        return kullaniciService.changePassword(aktif, body);
    }

    @Transactional
    @DeleteMapping("/users/me")
    public Map<String, Object> deleteMe(@RequestAttribute("aktifKullanici") Kullanici aktif) {
        return kullaniciService.deleteMe(aktif);
    }

    @Transactional
    @DeleteMapping("/admin/users/{id}")
    public Map<String, Object> adminDelete(
            @RequestAttribute("aktifKullanici") Kullanici aktif,
            @PathVariable Long id) {
        return kullaniciService.adminDelete(aktif, id);
    }

    @GetMapping("/admin/users")
    public List<KullaniciGorunum> adminListAll(
            @RequestAttribute("aktifKullanici") Kullanici aktif) {
        return kullaniciService.adminListAll(aktif);
    }
}
