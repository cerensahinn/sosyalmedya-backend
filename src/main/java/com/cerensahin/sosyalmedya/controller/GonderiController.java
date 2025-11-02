package com.cerensahin.sosyalmedya.controller;

import com.cerensahin.sosyalmedya.dto.GonderiDetay;
import com.cerensahin.sosyalmedya.dto.GonderiGorunum;
import com.cerensahin.sosyalmedya.dto.GonderiOlusturIstegi;
import com.cerensahin.sosyalmedya.dto.YorumGorunum;
import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.service.GonderiService;
import org.springframework.data.domain.Page; //Verileri sayfalama  için.
import org.springframework.web.bind.annotation.*;//@RestController, @RequestMapping, @GetMapping … → Spring MVC’nin web annotation’ları.

import java.util.Map;//JSON tipinde yanıt döndürmek için.

/*
Controller katmanı, Spring Boot projelerinde dış dünyadan (örneğin Postman, frontend, mobil uygulama gibi
istemcilerden) gelen HTTP isteklerini karşılayan ve yanıt üreten katmandır.
Yani uygulamanın giriş kapısı gibidir.
 */

@RestController
//bir sınıfın REST API isteği (HTTP request) alacağını ve
//JSON formatında cevap döneceğini söyleyen bir Spring Boot anotasyonudur.

@RequestMapping("/api/posts")
//Bu sınıftaki tüm metodların URL’si /api/posts ile başlar
//@RequestMapping, bir endpoint’in (istek adresinin) hangi URL üzerinden çağrılacağını
//ve hangi HTTP metodunu (GET, POST, PUT, DELETE) kullanacağını belirleyen Spring anotasyonudur.
//Bu metot / sınıf şu URL’den gelen şu tip istekleri karşılasın der burada da /api/posts
public class GonderiController {

    private final GonderiService gonderiService;
    /*
GonderiController, GonderiService sınıfının metodlarını kullanmak istiyor. (bağımlılıkların oluşturulması dependency)
private → sadece bu sınıf içinde erişilebilir.
final → bu değişken bir kere atandıktan sonra değiştirilemez.
Controller, bir kere hangi Service ile çalışacağını belirler ve sonradan değiştirilmez.
     */

    public GonderiController(GonderiService gonderiService) {
        this.gonderiService = gonderiService;
        /*
Bu, bir constructor (yapıcı metot).
Sınıfın bir nesnesi oluşturulduğunda ilk çalışan metot budur.Buradaki amaç:
Spring Boot, bu sınıfı oluştururken (new ile değil otomatik olarak)
GonderiService nesnesini bulur ve buraya enjekte eder (Dependency Injection).
Bana dışarıdan verilen GonderiService nesnesini al,
sınıfın içindeki gonderiService değişkenine ata.
         */
    }

    @GetMapping
    //@GetMapping Bu anotasyon, HTTP GET isteklerini karşılamak için kullanılır.
    //Yani kullanıcı Postman veya frontend’den GET /api/posts isteği gönderdiğinde,
    //Spring bu metodu çalıştırır.Bir şey listelemek veya görüntülemek istiyorsan → @GetMapping kullan
    //api/posts → tüm gönderileri getir
    //api/users → tüm kullanıcıları getir
    //@RequestParam — Spring Boot’ta HTTP isteğindeki (URL’deki) sorgu parametrelerini almak için kullanılır.
    public Page<GonderiGorunum> liste(@RequestParam(defaultValue = "0") int page, //sayfa 0 (yani ilk sayfa)
//                                      //Bu, URL’den gelen sorgu parametresini alır. GET /api/posts?page=2&size=5
                                      //page = 2 size = 5
                                      @RequestParam(defaultValue = "10") int size) { //her sayfada 10 gönderi
        return gonderiService.liste(page, size);

    }
    //Page<GonderiGorunum> Bu metod bir sayfalanmış gönderi listesi döndürüyor
    //Controller iş mantığı içermez — sadece yönlendirir.
    //Bu satırda Controller, görevi Service katmanına devrediyor Yani:
    //“Hey GonderiService, bana  page ve size değerlerine göre gönderi listesini getir.”

    @GetMapping("/user/{kullaniciId}")
    //Bu metot, /api/posts/user/{kullaniciId} adresinden gelen GET isteğini karşılasın
    public Page<GonderiGorunum> kullaniciGonderileri(@PathVariable Long kullaniciId,
                                                     //@PathVariable Long kullaniciId
                                                     //Bu, URL içindeki değişkeni yakalar.GET /api/posts/user/5
                                                     //burada {kullaniciId} = 5 olur.Spring, @PathVariable sayesinde 5 değerini alır
                                                     // ve metot parametresine yerleştirir:
                                                     @RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return gonderiService.kullaniciGonderileri(kullaniciId, page, size);
//Ben sadece isteği aldım.Asıl işi GonderiService yapsın
        /*
kullaniciId, page, size değerlerini service’e gönderir.Service katmanı bu bilgileri alır,
repository (veritabanı) katmanına gider.Repository şu işlemi yapar: findByKullanici_Id(kullaniciId, pageable);
yani “bu kullanıcıya ait gönderileri, sayfalanmış şekilde getir
Controller da bu sonucu JSON olarak frontend’e yollar.

         */
    }
    //@RequestMapping sınıf düzeyinde:
    //Bu, tüm endpoint’ler için bir temel adres (root path) belirler.
    //@GetMapping, @PostMapping, @PutMapping vb. metot düzeyinde:
    //Bu, o metoda özel alt adres (sub path) belirler.


    @GetMapping("/{id}")
    public GonderiDetay detay(@PathVariable Long id) { // ıd degerini pathvariable yakalar tutar
        return gonderiService.detay(id, 0, 10);
    }//Controller, isteği aldıktan sonra işi service katmanına devrediyor.Yani:
    //“Hey GonderiService, bana ID’si id olan gönderinin detayını getir // 0 dan başla 10 a kadar

    @PostMapping
    /*
Bu anotasyon, HTTP POST isteklerini karşılar.
Yani Postman veya frontend tarafından biri “ oluştur-ekle” isteği gönderirse,
bu metod çalışır.
     */
    public Map<String, Object> olustur(@RequestAttribute("aktifKullanici") Kullanici aktif,
                                       @RequestBody GonderiOlusturIstegi body) {
        //@RequestAttribute, istek (request) içinde daha önce eklenmiş olan bir
        // değeri (örneğin giriş yapmış kullanıcıyı) almak için kullanılır.
        //Yani sistemde bir kullanıcı giriş yaptıysa,
        //token interceptor sayesinde onun kimliği (Kullanici nesnesi) istekle birlikte taşınır
        //ve bu şekilde metoda ulaşır.Kullanıcı aktifse
        //@RequestBody GonderiOlusturIstegi body Bu da istek gövdesindeki (request body) JSON verisini alır.
        //Yani kullanıcı Postman’den gönderdiği JSON'I Spring otomatik olarak GonderiOlusturIstegi sınıfına dönüştürür.

        return gonderiService.olustur(aktif, body);
        //Hey GonderiService, bana aktif kullanıcıyla birlikte bu gönderiyi oluştur
    }
// @PutMapping güncelleme işlemlerinde
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
