package com.cerensahin.sosyalmedya.ortak.handler;

import com.cerensahin.sosyalmedya.controller.KullaniciController;
import com.cerensahin.sosyalmedya.ortak.exception.kullanici.EmailZatenKayitliException;
import com.cerensahin.sosyalmedya.ortak.exception.kullanici.EskiSifreHataliException;
import com.cerensahin.sosyalmedya.ortak.exception.kullanici.KullaniciBulunamadiException;
import org.springframework.dao.DataIntegrityViolationException;
//Veritabanı işlemlerinde “bütünlük ihlali (data integrity violation)” olduğunda fırlatılır.
//Yani:Aynı email’i ikinci kez eklemek (unique constraint hatası)
//Null geçilmemesi gereken bir kolona null göndermek
import org.springframework.http.HttpStatus;
//HTTP durum kodlarını (status codes) temsil eden bir enum
import org.springframework.http.ResponseEntity; //Hangi hata gelirse gelsin, durum kodunu dinamik belirleyeyim
//yani bazen 400, bazen 403 döndüreyim diye kullanıyoruz
import org.springframework.web.bind.annotation.ExceptionHandler; //Belirli exception türlerini dinleyip, onlara özel işlem yapmanı sağlar
import org.springframework.web.bind.annotation.ResponseBody;
//@RestController veya @RestControllerAdvice zaten @ResponseBody’yi otomatik olarak içerdiği için genellikle ayrıca yazmana gerek kalmaz.
//Yani return ettiğin şey bir HTML sayfası değil, JSON veya text olarak kullanıcıya döner.Cevap gövdeye yazılır
import org.springframework.web.bind.annotation.ResponseStatus;
//Bir metodun hangi HTTP durum kodunu döneceğini belirtir.
import org.springframework.web.bind.annotation.RestControllerAdvice;
//Tüm controller’ları dinle, bir hata oluşursa burada tanımlı @ExceptionHandler metodlarını uygula.

import java.util.Map;

@RestControllerAdvice(assignableTypes = {KullaniciController.class})
//hata yakalayıcı sınıf anlamına gelir.parantez içinde şu ek bilgi var
//sadece kullaniciController sınıfı içinde oluşan hataları yakalayacağım
public class KullaniciExceptionHandler {

    @ExceptionHandler(KullaniciBulunamadiException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> notFound(KullaniciBulunamadiException ex) {
        return Map.of(
                "mesaj", ex.getMessage(),
                "kod", "KULLANICI_404",
                "kullaniciId", ex.getKullaniciId()
        );
    }

    @ExceptionHandler(EmailZatenKayitliException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> emailExists(EmailZatenKayitliException ex) {
        return Map.of(
                "mesaj", ex.getMessage(),
                "kod", "KULLANICI_409",
                "email", ex.getEmail()
        );
    }

    @ExceptionHandler(EskiSifreHataliException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> wrongOldPwd(EskiSifreHataliException ex) {
        return Map.of(
                "mesaj", ex.getMessage(),
                "kod", "KULLANICI_SIFRE_400"
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    //“Çakışma var, işlem gerçekleştirilemez”
    public Map<String, Object> uniqueViolation(DataIntegrityViolationException ex) {
        //veritabanında unique constraint ihlali olduğunda bu hata fırlatılır aynı maille 2 kayıt eklenmeye çalışıldığında
        //DataIntegrityViolationException, hata mesajını Spring değil veritabanı (örneğin PostgreSQL) üretir.
        //O yüzden bu hata türünde mesajın kaynağı derindedir (en alt katmanda) — yani getMostSpecificCause() ile ulaşmamız gerekir.
        String detay = ex.getMostSpecificCause() != null
                //ex parametresi DataIntegrityViolationException türündedir.
                //Bu hata, veritabanında bir bütünlük (integrity) ihlali olduğunda Spring tarafından fırlatılır.
                // ex.getMessage() metodu, hatanın genel mesajını döner.
                //ex.getMostSpecificCause() bu hatanın en spesifik (özgül) nedenini döner.
                ? ex.getMostSpecificCause().getMessage()
                //ex.getMostSpecificCause().getMessage()
                //Bu, veritabanının döndürdüğü en açıklayıcı hata mesajını getirir.
                : ex.getMessage();
        return Map.of(
                "mesaj", "Benzersiz alanlardan biri zaten kayıtlı.",
                "kod", "KULLANICI_409_CONSTRAINT",
                "detay", detay
                //Eğer ex.getMostSpecificCause() null değilse (yani altta daha spesifik bir hata varsa),
                //ondan gelen mesajı al.
                //Yoksa (null ise), ex.getMessage() yani genel mesajı kullan.
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    //Bu metot, IllegalArgumentException türündeki hataları yakalayacak
    public @ResponseBody ResponseEntity<Map<String, Object>> illegalArg(IllegalArgumentException ex) {
        //Yani return ettiğin şey bir HTML sayfası değil, JSON veya text olur.
        //Bir HTTP yanıtı döndüreceğim (ResponseEntity),
        //gövdesinde (body) bir Map<String,Object> olacak.
        //Çünkü ResponseEntity:
        //hem HTTP durum kodunu (HttpStatus.BAD_REQUEST gibi),
        //hem de dönecek veriyi (body)
        //tek bir yapı içinde döndürmeni sağlar.
        boolean adminMesaj = ex.getMessage() != null
                //Bu, exception nesnesinin mesajını getirir.
                //Yani daha önce bir yerde:
                //throw new IllegalArgumentException("Bu işlemi yapmak için admin rolü gerekir");
                //gibi bir hata fırlatılmışsa,
                //ex.getMessage() → "Bu işlemi yapmak için admin rolü gerekir" olur.
                && ex.getMessage().toLowerCase().contains("admin rolü gerekir");
        //Bu, “mesaj boş (null) değil mi?” kontrolüdür.
        //Çünkü bazı hatalar mesaj içermeyebilir.
        //Eğer direkt .toLowerCase() gibi işlemleri null değer üzerinde yaparsan program hata verir (NullPointerException).
        //O yüzden önce “null değil mi” diye kontrol ediyor.
        //Eğer mesaj varsa → devam et
        //Bu, mesajın tamamını küçük harfe çevirir..contains("admin rolü gerekir")
        //Bu kısım da şunu kontrol eder:
        //“Mesajın içinde ‘admin rolü gerekir’ ifadesi geçiyor mu?”
        //Eğer geçiyorsa → true,

        HttpStatus status = adminMesaj ? HttpStatus.FORBIDDEN : HttpStatus.BAD_REQUEST;
        //Eğer adminMesaj true ise → HttpStatus.FORBIDDEN,
        //değilse → HttpStatus.BAD_REQUEST.
        //HttpStatus.FORBIDDEN = 403 → “Erişim yasak, yetki yok”
        //HttpStatus.BAD_REQUEST = 400 → “İstek geçersiz”

        Map<String, Object> body = Map.of(
                "mesaj", ex.getMessage(),
                "kod", adminMesaj ? "YETKI_403" : "GECERSIZ_ISTEK_400"
        );

        return new ResponseEntity<>(body, status);
        //Bu, ResponseEntity sınıfından yeni bir nesne oluşturuyor.
        //ResponseEntity, Spring’te bir HTTP cevabını temsil eder.
        //Yani içinde 3 şey taşır:
        //body → cevabın içeriği (JSON, text, Map vs.)
        //status → HTTP durum kodu (200, 404, 403, 400 vs.)
        //(opsiyonel) header → özel HTTP başlıkları
        //İlk parametre → HTTP cevabının gövdesi (body)
        //İkinci parametre → HTTP cevabının durum kodu (status)
        //Yani bu satır şunu diyor:
        //“body değişkenindeki Map verisini, status değişkeninde belirtilen HTTP koduyla birlikte döndür.”

        //Hata tipine göre status kodu değişsin
        //Hata mesajına göre farklı JSON body gelsin
        //Gerekirse HTTP header ekleyelim
        //gibi durumlarda, artık Spring’in otomatik oluşturduğu cevabı istemeyiz.Dolaısıyla NESNEYİ BİZ OLUŞTURURUZ.
    }
}
