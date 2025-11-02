package com.cerensahin.sosyalmedya.controller;

import com.cerensahin.sosyalmedya.dto.GonderiDetay;
import com.cerensahin.sosyalmedya.dto.GonderiGorunum;
import com.cerensahin.sosyalmedya.dto.GonderiOlusturIstegi;
import com.cerensahin.sosyalmedya.dto.YorumGorunum;
import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.service.GonderiService;
import org.springframework.data.domain.Page; 
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController

@RequestMapping("/api/posts")

public class GonderiController {

    private final GonderiService gonderiService;

    public GonderiController(GonderiService gonderiService) {
        this.gonderiService = gonderiService;

    }

    @GetMapping

    public Page<GonderiGorunum> liste(@RequestParam(defaultValue = "0") int page, 

                                      @RequestParam(defaultValue = "10") int size) { 
        return gonderiService.liste(page, size);

    }

    @GetMapping("/user/{kullaniciId}")

    public Page<GonderiGorunum> kullaniciGonderileri(@PathVariable Long kullaniciId,

                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return gonderiService.kullaniciGonderileri(kullaniciId, page, size);

    }

    @GetMapping("/{id}")
    public GonderiDetay detay(@PathVariable Long id) { 
        return gonderiService.detay(id, 0, 10);
    }

    @PostMapping

    public Map<String, Object> olustur(@RequestAttribute("aktifKullanici") Kullanici aktif,
                                       @RequestBody GonderiOlusturIstegi body) {

        return gonderiService.olustur(aktif, body);

    }

    @PutMapping("/{id}")
    public Map<String, Object> guncelle(@RequestAttribute("aktifKullanici") Kullanici aktif,
                                        @PathVariable Long id,
                                        @RequestBody GonderiOlusturIstegi body) {
        return gonderiService.guncelle(aktif, id, body);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> sil(@RequestAttribute("aktifKullanici") Kullanici aktif,
                                   @PathVariable Long id) {
        return gonderiService.sil(aktif, id);
    }

    @PostMapping("/{id}/view")
    public Map<String, Object> view(@PathVariable Long id) {
        return gonderiService.view(id);
    }

    @PostMapping("/{id}/comments")
    public Map<String, Object> yorumEkle(@RequestAttribute("aktifKullanici") Kullanici aktif,
                                         @PathVariable Long id,
                                         @RequestBody Map<String, String> body) {
        return gonderiService.yorumEkle(aktif, id, body.get("icerik"));
    }

    @GetMapping("/{id}/comments")
    public Page<YorumGorunum> yorumlar(@PathVariable Long id,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size) {
        return gonderiService.yorumlar(id, page, size);
    }

    @PostMapping("/{id}/likes")
    public Map<String, Object> begen(@RequestAttribute("aktifKullanici") Kullanici aktif,
                                     @PathVariable Long id) {
        return gonderiService.begen(aktif, id);
    }

    @DeleteMapping("/{id}/likes")
    public Map<String, Object> begeniyiGeriAl(@RequestAttribute("aktifKullanici") Kullanici aktif,
                                              @PathVariable Long id) {
        return gonderiService.begeniyiGeriAl(aktif, id);
    }
}
