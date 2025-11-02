package com.cerensahin.sosyalmedya.service;

import com.cerensahin.sosyalmedya.dto.KullaniciGorunum; //Entity yerine DTO döndürmek güvenli ve kontrollüdür.
import com.cerensahin.sosyalmedya.dto.ParolaGuncelleIstegi;
import com.cerensahin.sosyalmedya.entity.Kullanici;

import java.util.List;//Birden fazla elemanı tutan Java koleksiyonu.
import java.util.Map;//Anahtar-değer çiftiyle veri döndürmemizi sağlar (örnek: { "mesaj": "Silme işlemi başarılı"

//Service katmanı, Controller (dış istekleri alan katman) ile Repository (veritabanı işlemlerini yapan katman)
// arasında bir köprü gibidir.
//Burada amaç, veritabanı işlemleriyle dış dünyayı (API’yi) doğrudan bağlamak yerine, araya bu katmanı koyarak
// iş kurallarını (business logic) merkezi bir yerde yönetmektir.

public interface KullaniciService {
    /*
 Bu bir arayüz (interface).
Yani burada sadece “ne yapılacak” tanımlanır, “nasıl yapılacağı” değil.
Gerçek işlemler bu interface’in KullaniciServiceImpl adlı sınıfında yapılır.
     */

    KullaniciGorunum getById(Long id);
    /*
 Belirtilen id’ye sahip kullanıcıyı getirir ve DTO formatında döndürür.
KullaniciGorunum → sadece gerekli kullanıcı bilgilerini döndürür (örneğin e-posta, ad soyad).
Entity yerine DTO dönülmesinin sebebi:
🔹 güvenlik (şifre gibi alanları gizlemek)
🔹 dış dünyaya sade veri göstermek.
     */

    Map<String, Object> changePassword(Kullanici aktif, ParolaGuncelleIstegi body);
    /*
Sisteme giriş yapmış (aktif) kullanıcının şifresini değiştirir.
Parametreler:
Kullanici aktif → şu anda giriş yapmış kullanıcı (token’dan bulunur).
ParolaGuncelleIstegi body → yeni şifre bilgilerini taşıyan DTO.
Dönüş tipi:
Map<String, Object> → örnek: { "mesaj": "Parola başarıyla güncellendi" }.
//Controller’da aktiflik bilgisi tutulur.
     */

    Map<String, Object> deleteMe(Kullanici aktif);
    /*
Giriş yapmış kullanıcı, kendi hesabını silmek isterse bu metot çağrılır.

Parametre: aktif kullanıcı.

Dönüş: işlemin sonucunu gösteren mesaj map’i.

Örnek: { "mesaj": "Hesabınız silindi" }
     */

    Map<String, Object> adminDelete(Kullanici aktif, Long id);
    /*
  Sadece admin rolüne sahip kullanıcı başka bir kullanıcıyı silebilir.
Parametreler:

aktif → şu anda işlem yapan kullanıcı (admin olmalı).

id → silinmek istenen kullanıcının kimliği.
     */

    List<KullaniciGorunum> adminListAll(Kullanici aktif);
    /*
Admin, sistemdeki tüm kullanıcıları listeleyebilir.

aktif admin kontrolü yapılır.

Dönen veri: kullanıcıların DTO listesi (şifre içermez).
     */
}
