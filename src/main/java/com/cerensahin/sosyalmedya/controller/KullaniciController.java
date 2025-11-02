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
    //burada özellikle transactional kullanıyoruz ki kullanıcı silindiğinde ona ait işlemler de silinsin
    @DeleteMapping("/users/me")
    public Map<String, Object> deleteMe(@RequestAttribute("aktifKullanici") Kullanici aktif) {
        return kullaniciService.deleteMe(aktif);
        //Bu endpoint, aktif (giriş yapmış) kullanıcının kendi hesabını silmesini sağlıyor.
        //Kullanıcı silinince ona ait tüm gönderi, yorum, beğeni kayıtları da birlikte siliniyor.
    }

    @Transactional
    @DeleteMapping("/admin/users/{id}")
    public Map<String, Object> adminDelete(
            @RequestAttribute("aktifKullanici") Kullanici aktif,
            @PathVariable Long id) {
        return kullaniciService.adminDelete(aktif, id);
        //Bu endpoint, admin’in başka bir kullanıcıyı silmesini sağlar.
        //Kullanıcı ve ona ait tüm içerikler (post, yorum, beğeni) silinir.
    }

    @GetMapping("/admin/users")
    public List<KullaniciGorunum> adminListAll(
            @RequestAttribute("aktifKullanici") Kullanici aktif) {
        return kullaniciService.adminListAll(aktif);
        //bu metod, sistemdeki tüm kullanıcıları listelemek için admin’e özel bir endpoint.
    }
}
