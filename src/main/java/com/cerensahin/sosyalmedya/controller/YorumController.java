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
        //Dışarıdan gelen istek (Request Body) (yorum ekleme isteğinin json'ı
        //  "icerik": "Bu gönderi harika olmuş!" gibi
        //burada özel bir DTO sınıf (YorumOlusturIstegi) kullanılmamış. Bunun yerine
        /*
@RequestBody Map<String, String> body Bu satır şunu yapıyor 👇
“İstek gövdesindeki (JSON’daki) veriyi al, bir Map’e dönüştür.” Yani bu { "icerik": "Bu gönderi harika olmuş!" }json'ı aldı
Map<String, String> body = Map.of("icerik", "Bu gönderi harika olmuş!"); bu java nesnesine dönüştürdü yani
JSON → Map (anahtar-değer çiftleri olarak) "icerik" → key (anahtar)
"Bu gönderi harika olmuş!" → value (değer)
Burada geri dönüş olarak da Map kullanılmış.
Yani hem gelen veri bir Map,
hem dönen cevap da bir Map.

         */

        String icerik = body.get("icerik");
        //body adlı map’in içinde icerik anahtarına karşılık gelen değeri bul
        //ve onu yorumIcerigi adlı değişkene ata.”
        String gonderiIdStr = body.get("gonderiId");

        if (icerik == null || icerik.trim().isEmpty()) {
            //.trim() → baştaki ve sondaki boşlukları sil
            //.isEmpty() → sonuç olarak metin tamamen boş mu, kontrol et
            //icerik == null → hiç gönderilmemiş veya icerik.trim().isEmpty() → sadece boşluklardan oluşmuş
            throw new IllegalArgumentException("icerik zorunlu.");
        }
        if (gonderiIdStr == null || gonderiIdStr.isBlank()) {
            throw new IllegalArgumentException("gonderiId zorunlu.");
        }
//|| gonderiIdStr.isBlank() Değişken var ama boş mu?” sorusunu sorar.
        //Burada da gonderi ıd boş mu ya da var ama içerisi mi boş diye bakar.
        // bir String yalnızca boşluklardan oluşuyorsa veya tamamen boşsa true döner.
        //Hem boş (“”) stringleri,Hem de sadece whitespace (boşluk, tab, newline gibi görünmeyen karakterleri) boş algılar
        Long gonderiId; //Bu satırda boş bir Long değişken tanımlanıyor.
       // Yani birazdan String olan gonderiIdStr sayıya çevrildikten sonra bu değişkende tutulacak.
        try {
            gonderiId = Long.parseLong(gonderiIdStr.trim());
            //Long.parseLong(...) → String’i Long sayıya dönüştürür bunu yaparken baş son boşlukları siliyor
            //Eğer kullanıcı yanlışlıkla "abc" veya "15a" gibi sayı olmayan bir şey gönderirse,
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("gonderiId sayısal olmalıdır.");
        }
        //bu hatayı alır.
        /*
NumberFormatException → Java içindeki teknik hata türüdür.(“Parse işlemi başarısız oldu.”)
IllegalArgumentException → Bizim API’miz açısından daha anlamlı bir hata türüdür.
(“Kullanıcının gönderdiği parametre geçersiz.”) Yani iki farklı hata türünün amacı amaç:
Teknik hatayı yakalayıp, iş anlamında daha uygun bir hata fırlatmak.
         */

        return yorumService.ekle(aktif, gonderiId, icerik.trim());
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> sil(@RequestAttribute("aktifKullanici") Kullanici aktif,
                                   @PathVariable Long id) {
        return yorumService.sil(aktif, id);
    }
}
