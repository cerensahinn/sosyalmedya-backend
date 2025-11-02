package com.cerensahin.sosyalmedya.config;

import org.springframework.context.annotation.Configuration;
//Bu sınıf bir Spring yapılandırma (configuration) sınıfıdır. Spring bunu otomatik tanır.
//Bu, Spring’e bu sınıfın bir “yapılandırma (configuration)” sınıfı olduğunu söyleyen kütüphanedir.
//Yani bu sınıf, uygulamanın nasıl davranacağını belirleyen ayarlar içeriyor.
//
//Spring Boot, uygulama başlarken proje içinde @Configuration ile işaretlenmiş sınıfları otomatik olarak tarar
// ve içindeki ayarları uygular.
//Çünkü bu sınıfın görevi web davranışını yapılandırmak
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//Interceptor (yani araya girici, denetleyici) nesneleri Spring’e kaydetmek için kullanılır.
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//Web isteklerini (URL, dosya erişimi vb.) yapılandırmak için kullanılan Spring arayüzüdür.
//WebMvcConfigurer, Spring MVC’nin özelleştirilebilmesini sağlayan bir arayüzdür (interface).
//Yani sen web tarafında bazı ayarları değiştirmek veya eklemek istiyorsan, bu arayüzü “implement” edersin.
//Bu arayüz sayesinde aşağıdaki metotları override edebilirsin:
//addInterceptors() → hangi Interceptor’lar aktif olacak
//addResourceHandlers() → hangi klasörler web’e açılacak
//addCorsMappings() → CORS izinleri
//configureViewResolvers() → görünüm ayarları
//Interceptor, gelen her HTTP isteğinin Controller’a ulaşmadan önce araya girip onu kontrol eden “nöbetçi” bir sınıftır.
@Configuration
public class WebYapilandirma implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;
//TokenInterceptor, kullanıcıdan gelen her istekte Authorization header’ındaki token’ı kontrol eder
//ve sadece geçerli token’lara izin verir.
    public WebYapilandirma(TokenInterceptor tokenInterceptor) {
        this.tokenInterceptor = tokenInterceptor;
        //Spring Boot uygulaması başlarken TokenInterceptor sınıfını @Component olarak tanır ve bir nesnesini oluşturur.
        //Sonra burada otomatik olarak bu sınıfa enjekte eder.
    }

    @Override
    //Bu metot, Spring’in WebMvcConfigurer arayüzünden gelen bir metodu ezmek (override etmek) için yazılmıştır.
    //InterceptorRegistry → Interceptor’ları kayıt ettiğimiz bir koleksiyon gibidir.
   // Spring her gelen isteği işlerken önce bu listeye bakar:
    // “Bu isteğe karşılık çalışacak bir Interceptor var mı?”
    //Buraya hangi Interceptor’lar hangi URL’lerde çalışacaksa onları ekleriz.
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(tokenInterceptor)
                //Bu satır, TokenInterceptor sınıfını sisteme tanıtır (kaydediyor).
                //Her istek geldiğinde, önce bu Interceptor kontrol etsin token kontrolü
                .addPathPatterns("/api/**")
        //Bu satır, Interceptor’un hangi URL yollarında çalışacağını belirler.
                //Tüm API isteklerinde kimlik doğrulaması yap
                .excludePathPatterns("/api/auth/**");
        //ama bu yolları hariç tut
        //api/auth/login
        //api/auth/signup
        //api/auth/logout
        //Bu yollar Interceptor’dan geçmez çünkü:
        //Bu işlemler sırasında kullanıcı henüz giriş yapmamıştır.
        //Dolayısıyla token yoktur.
        //TokenInterceptor’dan geçmesi, aslında “bu isteğin güvenlik kontrolünden geçmesi” demektir 🔒
        //
        //Yani bu, Spring Boot uygulamanda “kullanıcı gerçekten giriş yapmış mı, token’ı geçerli mi?”
        // sorularının cevaplandığı aşamadır.


    }
    @Override
    //addResourceHandlers() metodunu override ederek kendi dosya erişim kuralını tanımlıyorsun.
    public void addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry registry) {
        //Spring, dosyaları hangi URL’den sunacağını bu registry üzerinden öğreniyor
        String absPath = java.nio.file.Path.of("uploads").toAbsolutePath().toUri().toString();
        //Bu satır “uploads” adlı klasörün bilgisayarındaki tam (absolute) yolunu buluyor.
        //Path.of("uploads")	“uploads” klasörüne bir yol nesnesi oluşturur
        //.toAbsolutePath()	Bu yolu tam adres haline getirir (kökten başlayan)
        //.toUri()	Dosya sistem yolunu URI formatına çevirir (file:/...)
        //.toString()	Bu URI’yi String’e dönüştürür
        registry.addResourceHandler("/uploads/**")
      //bu satır, dışarıdan gelen HTTP isteklerinden hangi yolların dosya isteği olduğunu belirler.
                .addResourceLocations(absPath);
        //Bu /uploads/... istekleri geldiğinde, git absPath klasöründeki dosyaları göster.
        //Bu satır, bir üstte yakalanan /uploads/** yollarına karşılık hangi klasörden dosya yükleneceğini belirtir.
        //Kullanıcı bir dosya (örneğin fotoğraf, video, pdf) yüklediğinde o dosya bilgisayarına kaydediliyor.
               // Ama bu dosyaya başka kullanıcılar (veya ön yüz) tarayıcıdan bir linkle ulaşabilsin istiyorsun.
    }
//Bu kısım projenin dosya erişimi (örneğin gönderilere yüklenen fotoğraflar) ile ilgilidir.
//Yani Spring’e “uploads klasöründeki dosyalara web üzerinden nasıl erişileceğini” öğretiyorsun

}
