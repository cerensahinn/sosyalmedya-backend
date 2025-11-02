package com.cerensahin.sosyalmedya.config; //Bu sınıf “config” paketinde.
// Çünkü bu bir yapılandırma (configuration) bileşenidir — uygulamanın çalışma düzenini belirler.

import com.cerensahin.sosyalmedya.entity.ErisimTokeni;
import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.ortak.exception.kimlik.TokenGecersizException;
import com.cerensahin.sosyalmedya.ortak.exception.kimlik.TokenSuresiDolmusException;
import com.cerensahin.sosyalmedya.repository.ErisimTokeniRepository;
import jakarta.servlet.http.HttpServletRequest;//gelen HTTP isteğini temsil eder.
//Yani kullanıcı (Postman, tarayıcı veya frontend) API’ye bir istek attığında,
//Spring bu isteği bir HttpServletRequest nesnesine dönüştürür.(İsteğin body sini url sini vs tutar)
import jakarta.servlet.http.HttpServletResponse;//gönderilecek yanıtı temsil eder.
import org.springframework.stereotype.Component; //bir sınıfın Spring tarafından otomatik olarak oluşturulup yönetilmesini (bean haline getirilmesini) sağlar.
//Spring, bu sınıfı sen oluştur, uygulamaya ekle ve ihtiyaç duyulduğunda otomatik olarak kullanıma hazır hale getir
//uygulama ayağa kalkarken bu sınıfı otomatik olarak belleğe yükler
import org.springframework.web.servlet.HandlerInterceptor;//Yani “her istek Controller’a ulaşmadan önce
// şuradan geçsin” demek istiyorsan, bunu kullanırsın.Spring, Controller’a ulaşmadan önce bana uğra; ben token’ı kontrol edeceğim

import java.time.LocalDateTime;
// bu sınıf, Spring Security kullanmadan token bazlı kimlik doğrulama yapmanı sağlar.
@Component
public class TokenInterceptor implements HandlerInterceptor {
    //HandlerInterceptor arayüzünü (interface) implemente ettiği için,Spring her istek geldiğinde bu sınıfın preHandle() metodunu çalıştırır.
    //Yani Her HTTP isteği önce buraya uğrar, buradan “geçiş izni” alır.

    private final ErisimTokeniRepository erisimTokeniRepository;
    //Token’ları veritabanından bulmak için repository’ye ihtiyaç vardır.

    public TokenInterceptor(ErisimTokeniRepository erisimTokeniRepository) {
        this.erisimTokeniRepository = erisimTokeniRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //preHandle() metodu, her HTTP isteği Controller’a ulaşmadan önce otomatik olarak çalışır.
        //HttpServletRequest request Bu nesne, gelen isteğin detaylarını içerir. (Yani kullanıcı ne çağırmış, hangi header’ı göndermiş, hangi URL’e gitmiş gibi tüm bilgileri burada bulabilirsin.)
        //HttpServletResponse response Bu nesne, isteğe verilecek cevabı temsil eder.
        //Yani kullanıcıya geri ne döneceğini bununla kontrol edebilirsin.
        //Object handler Bu parametre, Spring’in hangi Controller metodunun çağrılacağını gösterir.
        //Yani istek /api/posts ise, bu handler GonderiController.liste() metoduna denk gelir.
//true → “Her şey yolunda, Controller’a devam et.” false → “Dur! Controller’a gitme.”
        String path = request.getRequestURI();
        //String path = request.getRequestURI(); Amacı: Gelen HTTP isteğinin yolunu almak.
        //Neden lazım? Çünkü bazı yollar kimlik doğrulamasız erişilebilir olmalı (signup, login, logout gibi).
        if (path.startsWith("/api/auth")) return true;
        //Eğer istek /api/auth ile başlayan bir endpoint’e gidiyorsa, bu isteği engelleme,
        // token kontrolünü yapma; Controller’a bırak.
        // /api/auth/signup → kullanıcı henüz girmedi, token olamaz.
        // /api/auth/login → giriş yapmak için token gerekmez.
        // /api/auth/logout → bazen token ister, ama burada tasarım gereği Interceptor’u geçmesi istenmiş
        //Özet: Beyaz liste (whitelist). “Auth” alanındaki uçlar serbest, diğer tüm yollar korumalı.

        String auth = request.getHeader("Authorization");
        //request.getHeader("Authorization") → gelen isteğin header bilgileri arasından Authorization başlığını alır.
        //Yani postman dan gelen Authorization: Token 123abc456 bu kısmı
        if (auth == null || !auth.startsWith("Token ")) {
            //Eğer kullanıcı hiç token göndermediyse: ya da var ama doğru formatta değilse.
            //Eğer Authorization başlığı yoksa
            //veya ‘Token ’ kelimesiyle başlamıyorsa,
            //bu istek geçersizdir.”
            throw new TokenGecersizException(null); //null gönderilmiş?
            //Çünkü bu noktada henüz herhangi bir token değeri elimizde yok //Geçersiz token: null
        }
        String deger = auth.substring("Token ".length()).trim();

        ErisimTokeni token = erisimTokeniRepository.findByDegerAndAktifTrue(deger)
                .orElseThrow(() -> new TokenGecersizException(deger));
        // Token veritabanında kayıtlı ve aktif mi? (yani sistem tanıyor mu?

        if (token.getSonKullanmaZamani() != null &&
                token.getSonKullanmaZamani().isBefore(LocalDateTime.now()))
            //Token zaman açısından hâlâ geçerli mi? (süresi bitmiş mi?)
        //Geçerlilik zamanı boş mu ve  token’ın son kullanma zamanı şu andan önce mi öyleyse
            {
            throw new TokenSuresiDolmusException(token.getSonKullanmaZamani());
        }

        Kullanici aktif = token.getKullanici(); //token ile ilişkili kullanıcıyı alıyor.
        request.setAttribute("aktifKullanici", aktif);
        //“Bu isteğe aktifKullanici adında bir bilgi ekle, değeri de aktif nesnesi (yani Kullanici) olsun.
        //Bu isteği yapan kişi → aktif adlı Kullanici nesnesi
        //Controller tarafında @RequestAttribute("aktifKullanici") ile bu bilgiyi yakalayacağız
        //Burada Spring otomatik olarak request içindeki "aktifKullanici" değerini bulur
        //ve parametreye (Kullanici aktif) enjekte eder
        return true;
    }
}
