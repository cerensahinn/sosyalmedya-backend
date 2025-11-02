package com.cerensahin.sosyalmedya.repository;

import com.cerensahin.sosyalmedya.entity.Gonderi;
import org.springframework.data.domain.Page; //Sayfalı veri + bilgi” taşıyan özel koleksiyon -“Cevabı” taşır
import org.springframework.data.domain.Pageable; //sayfa ayarları için (0, 10) - 0. sayfadan başla her sayfa  10 kayıt
import org.springframework.data.jpa.repository.JpaRepository;
/*
Veritabanında 100 tane kullanıcı var.
Sen bu kullanıcıları API ile getiriyorsun.
Ama hepsini bir anda getirmek istemiyorsun
Bu yüzden diyorsun ki:
“Ben her sayfada sadece 10 kullanıcı getireyim. İlk sayfada ilk 10 kişi, ikinci sayfada sonraki 10 kişi...”
İşte burada Page ve Pageable devreye giriyor
 */

public interface GonderiRepository extends JpaRepository<Gonderi, Long> {
    Page<Gonderi> findByKullanici_IdOrderByOlusturmaZamaniDesc(Long kullaniciId, Pageable pageable);
    // .. Id'li kullanıcıya ait gönderileri, oluşturulma zamanına göre yeniden eskiye (DESC) sırala
    // ve sayfalı şekilde getir
    Page<Gonderi> findAllByOrderByOlusturmaZamaniDesc(Pageable pageable);
    //Tüm kullanıcıların gönderilerini oluşturulma zamanına göre yeniden eskiye sırala ve sayfalı döndür

    void deleteByKullanici_Id(Long kullaniciId);
    //Gonderi tablosunda kullanici_id = verilen id olan o kullanıcıya bağlı gönderi kayıtlarını siler

}
