package com.cerensahin.sosyalmedya.repository;

import com.cerensahin.sosyalmedya.entity.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {
    //Ben Kullanici tablosuyla çalışacağım ve bu tablonun birincil anahtar (id) tipi Long olacak.

    Optional<Kullanici> findByEmail(String email); // bu mail adresiyle kayıtlı bir kullanıcı var mı bul getir
    //Eğer bu metotlar şöyle olsaydı:
    //Kullanici findByEmail(String email);
    //ve bu email’e ait kullanıcı veritabanında yoksa, JPA null döndürürdü.
    //Sonra bu değeri kullandığın yerde (örneğin service katmanında):
    //NullPointerException alırdık
    //Optional kullanarak bunu önlüyoruz mail yoksa bile boş (Optional.empty()) optional döner
    boolean existsByEmail(String email); // bu mail adresiyle kayıtlı bir kullanıcı var mı true/false döner

    Optional<Kullanici> findByKullaniciAdi(String kullaniciAdi);// bu kullanıcı adıyla kayıtlı bir kullanıcı var mı bul getir
    boolean existsByKullaniciAdi(String kullaniciAdi); // bu kullanıcı adıyla kayıtlı bir kullanıcı var mı true/false döner

/*
Durum	İhtiyacın	Doğru metod
Kayıt olurken	“Bu e-posta zaten var mı?”	existsByEmail
Giriş yaparken	“Bu e-postaya ait kullanıcıyı getir, şifresine bakacağım”	findByEmail
Profil görüntülerken	“Bu kullanıcının bilgilerini göster”	findByKullaniciAdi
Kullanıcı adı çakışmasın	“Bu kullanıcı adı alınmış mı?”	existsByKullaniciAdi
hiçbir SQL yazmazsın, Spring metot isimlerinden sorguyu kendi üretir
 */
    /*
    /*
Ama dikkat et 👀
Bu interface’in içinde hiç metot gövdesi yok.
Bu işi Spring senin yerine yapıyor.
Uygulama çalışırken (run tuşuna bastığında), Spring bu interface’i buluyor ve perde arkasında şunu yapıyor 👇

“Hmm, bu interface JpaRepository’den türemiş, ben bunun için gerekli sınıfı dinamik olarak üreteyim, bütün
CRUD metotlarını ben yazayım.”
Eğer ınterface değil de class olsaydı, Spring bunu yapamazdı.Tüm CRUD metotlarını senin yazman gerekirdi.
Çünkü Spring Data JPA’nın otomatik implementasyon mekanizması interface’lere özel çalışır
JpaRepository bir interface → class extend edemez.
🧠 Repository’lerde interface kullanmamızın temel sebebi, Spring Data JPA’nın (JpaRepository) sağladığı otomatik
CRUD ve sorgu üretimi özelliklerinden yararlanabilmek için zorunlu bir yapısal şart olmasıdır
     */


}
