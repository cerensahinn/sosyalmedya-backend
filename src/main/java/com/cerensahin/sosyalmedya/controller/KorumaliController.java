package com.cerensahin.sosyalmedya.controller;

import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.service.KorumaliService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
// Bu sınıf sadece token ile doğrulanmış (giriş yapmış) kullanıcıların erişebildiği “korumalı” bir endpoint sağlar.
/*
Aktif token ile erişim gerçekten çalışıyor mu?” Diye kontrol için var bu tamamen benim test etmem için

“@RequestAttribute("aktifKullanici") gerçekten kullanıcıyı getiriyor mu?”

gibi kontrolleri yapmak için kullanılabilir.Token doğrulama mekanizmasının gerçekten çalıştığını test etmek
 */
@RestController
@RequestMapping("/api/protected")
public class KorumaliController {

    private final KorumaliService korumaliService;
    //Burada KorumaliService, işin arka planında yapılacak işlemleri
    // (örneğin token doğrulaması, mesaj hazırlama, loglama vb.) içerir.

    public KorumaliController(KorumaliService korumaliService) {
        this.korumaliService = korumaliService;
    }//Spring diyor ki Sen KorumaliService’e ihtiyaç duyuyorsun, ben onu senin yerine enjekte edeyim.”

    @GetMapping("/ping")
    //Postman’den /api/protected/ping adresine bir GET isteği atıldığında,
    //aşağıdaki metot çalışacaktır.
    public Map<String, Object> ping(@RequestAttribute("aktifKullanici") Kullanici aktif) {
        //Bu, request’in içinden giriş yapmış kullanıcıyı çeker.
        //(Yani token doğrulandıysa ve Interceptor tarafından aktifKullanici eklendiyse,
        // o kullanıcı buraya otomatik olarak gelir.)
        var x = 5;
        return korumaliService.ping(aktif);
        //Aktif kullanıcıyı aldım, servise gönderiyorum. Sen gerekli işlemi yap, sonucu bana döndür
    }
}
