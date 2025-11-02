package com.cerensahin.sosyalmedya.ortak.handler;

import org.slf4j.Logger;
//Hataları,Uyarıları,Bilgilendirme mesajlarını,Debug (hata ayıklama) çıktıları gibi bilgileri kayıt altına almamızı sağlar.
//Böylece uygulama çalışırken ne olduğunu console’a veya dosyalara düzgün biçimde yazdırabilirsin.
import org.slf4j.LoggerFactory;
//Logger ile beraber kullanılır çünkü LoggerFactory sınıfı, Logger nesnesini oluşturmak için kullanılır.
//SLF4J kütüphanesinin içinde yer alan bir fabrika (factory) sınıfıdır.
//Görevi: İlgili sınıf için uygun bir Logger nesnesi üretmek.
//Java'da her sınıf kendi logger’ına sahip olmalıdır ki hangi sınıftan log geldiğini anlayabilelim.

import org.springframework.core.annotation.Order;
//“Spring bu sınıfları veya metodları bir sırayla çalıştıracaksa, hangisinin önce çalışacağını
// belirlemek için @Order kullanılır.”

import org.springframework.http.HttpStatus;
//HttpStatus, Spring’in org.springframework.http paketinde bulunan bir enum (enumaration) sınıfıdır.
//Bu enum, HTTP protokolünde kullanılan tüm standart durum kodlarını ve onların isimlerini içerir.
//200 → OK ✅ 404 → NOT_FOUND 🕵️ gibi.

import org.springframework.web.bind.annotation.*;
//Bu import’un başındaki * (yıldız), bu paketteki tüm önemli anotasyonları getiriyor:
//@RestControllerAdvice
//@ExceptionHandler
//@ResponseStatus üçü de bu paketten gelir.Bundan dolayı tek bir import ile hepsini kullanabiliyoruz.

import java.util.Map;

//SLF4J kütüphanesi kullanılarak loglama yapılmaktadır.

@RestControllerAdvice //Uygulamanın tamamında oluşan hataları burada topla, tek bir yerden kontrol et
@Order(100)
//Bir projede birden fazla @RestControllerAdvice sınıfı olabilir:
// Mesela biri kullanıcı hatalarını yakalar (KullaniciExceptionHandler),
//Diğeri sistemdeki genel hataları yakalar (GlobalExceptionHandler).
//Spring bu handler’ları bir sıra ile çalıştırır.
//@Order anotasyonu, bu sıralamayı belirler 👇
//Değer	Öncelik Küçük sayı	🥇 Daha önce çalışır Büyük sayı🥈 Daha sonra çalışır
//Bu handler, diğer özel handler’lardan sonra çalışsın. Yani en genel hataları en sonda yakalasın.” demektir.
//Önce spesifik (örneğin KullaniciBulunamadiException) handler’lar çalışır.
//Onlar hatayı yakalamazsa → GlobalExceptionHandler devreye girer ve yakalanmamış tüm hataları toplar

public class GlobalExceptionHandler {
    //Spring Boot bu sınıfı @RestControllerAdvice ile işaretlediğin için, tüm controller’lardan
    // gelen hatalar bu sınıfa yönlendiriliyor.

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    //sadece bu sınıfa ait, değiştirilemeyen ve tüm nesneler tarafından ortak kullanılan log adında
    // loglama aracı =LoggerFactory.getLogger(...) metodu, verdiğin sınıfın ismine göre özel bir logger oluşturur.
    //Burada parametre olarak GlobalExceptionHandler.class veriyorsun. Yani GlobalExceptionHandler sınıfı için
    // bir logger oluşturuldu.Sınıf adı verdik Hangi sınıfta hangi olay olmuş, loglardan kolayca takip edebiliiz

    @ExceptionHandler(IllegalArgumentException.class)
    //  @ExceptionHandler belirli türdeki hataları yakalayan özel bir metodu işaretlemek için kullanılır.
    //Eğer  IllegalArgumentException türünde bir hata oluşursa, bu metodu çalıştır.” demek.
    //uygunsuz bir parametre verildiğinde fırlatılan hata
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    //  @ResponseStatus= Bu metot çağrıldığında, HTTP cevabının durum kodunu şu değere ayarla
    //BAD_REQUEST → 400 numaralı HTTP durum kodudur.
    //Yani Bu metot çalışırsa, istemciye 400 Bad Request döndür (Hatalı İstek)

    public Map<String, Object> handleIllegalArgument(IllegalArgumentException ex) {
        //IllegalArgumentException hatası geldiğinde, bu hatayı parametre olarak al ve Map türünde bir cevap oluştur.” demek.
        log.warn("400 IllegalArgumentException: {}", ex.getMessage());
        //log.warn(...) = Uyarı seviyesinde bir log kaydı oluşturur.
        // ex.getMessage() Exception’ın mesajını alır
        //gelen ex.getMessage() değeri otomatik olarak {} yerine yazılır

        return Map.of(
                "mesaj", ex.getMessage(),
                "detaySinif", ex.getClass().getName() //"detaySinif" alanına exception’ın tam sınıf adı yazılır.
        );
        //Spring, bu Map yapısını otomatik olarak JSON formatına çevirip HTTP cevabı olarak döndürür.
        //anahtar ve değerleri vererek tek satırda küçük bir tablo (Map) oluşturmanı sağlar.
        //Bu projede, hata mesajını JSON olarak göndermek için kullanıld

    }

    @ExceptionHandler(Exception.class)
    //Eğer uygulama içinde herhangi bir yerde Exception türünde (veya ondan türeyen) bir hata oluşursa,
    // bu hatayı buradaki metoda yönlendir. özel olarak yakalanmamış tüm hataları kapsar.

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    //Bu metot çalışırsa, istemciye 500 Internal Server Error döndür (Sunucu Hatası)

    public Map<String, Object> handleGeneral(Exception ex) {
        //Metot ismi serbest ama burada “genel hataları yakalıyor” anlamına gelecek şekilde adlandırılmış.
        //Parametre olarak Exception nesnesi alıyor.
        //Bu, yakalanan hatanın kendisidir.

        log.error("500 Internal Server Error", ex);
        //Hata mesajını ERROR seviyesinde loglar.

        return Map.of(
                "mesaj", "Beklenmeyen bir hata oluştu.",
                "detaySinif", ex.getClass().getName(),
                "detayMesaj", ex.getMessage()
        );
        //ex bir Exception nesnesidir.

        //lobalExceptionHandler, @RestControllerAdvice + @Order(100) ile tüm controller’lardan çıkan
        // hataları merkezi olarak yakalıyor; IllegalArgumentException için 400, diğer tüm beklenmeyen hatalar
        // için 500 üretiyor, SLF4J ile uygun seviyede logluyor ve Map.of ile tutarlı JSON gövdeleri dönüyor.”

        //IllegalArgumentException--Bir metoda geçersiz veya uygunsuz bir parametre verildiğinde fırlatılan hata
        // orn ID negatifse hata

    }
}
