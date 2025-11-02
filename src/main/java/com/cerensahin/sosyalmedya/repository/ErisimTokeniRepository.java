package com.cerensahin.sosyalmedya.repository;

import com.cerensahin.sosyalmedya.entity.ErisimTokeni;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ErisimTokeniRepository extends JpaRepository<ErisimTokeni, Long> {

    Optional<ErisimTokeni> findByDegerAndAktifTrue(String deger);


    //Veritabanında değeri verilen token string’ini bulur,
    //Ayrıca aktif=true koşulunu da kontrol eder.
    /*
    Kullanıcı bir istek gönderdiğinde, header’daki token değeriyle sistemdeki kayıt eşleşiyor mu diye kontrol edilir.
Ayrıca token aktif mi, yani iptal edilmemiş mi (örneğin kullanıcı çıkış yapmamış mı) bakılır.

🎯Kullanıcının bir isteği gerçekten yapmaya yetkili olup olmadığını anlamak için,
gönderdiği token değerinin veritabanında kayıtlı ve aktif (geçerli) olduğunu kontrol eder.
     */
    Optional<ErisimTokeni> findTopByKullanici_IdAndAktifTrueOrderByOlusturmaZamaniDesc(Long kullanici_id);

    /*
    .. ıd li kullanıcıya ait en son oluşturulan (yeni tarihli) aktif token kaydını bulur.

 findTopBy... → “en üstteki (ilk) kaydı bul”

Kullanici_Id → “şu kullanıcıya ait olan”

AndAktifTrue → “aktif = true olan”

OrderByOlusturmaZamaniDesc → “oluşturulma zamanına göre tersten sırala (yeni olan önce gelsin)

🎯Burada amacımız logout sırasında Kullanıcının hangi token’ını iptal edeceğimizi bilmek
     */

    void deleteByKullanici_Id(Long kullaniciId);
    //ErisimTokeni tablosunda kullanici_id = verilen id olan o kullanıcıya bağlı token kayıtlarını  siler
/*
peki diğerleri optional iken bu neden void?
Burada ilk ikisi “veri arama (read)”,
sonuncusu ise “veri silme (delete)” işlemi yapıyor.
findBy... → Arama işlemi

“Bu koşula uyan bir kayıt var mı? Varsa getir.”

🔸 Arama sonuç verir de vermeyebilir.
🔸 Yani null olabilir.
🔸 Bu yüzden dönüş tipi: optional
deleteBy... → Eylem (işlem)
“Bu koşula uyan kayıt(ları) sil.”
🔸 Burada bir sonuç dönmez çünkü silme işlemi veri döndürmez.
Bu sorgu çalıştıktan sonra, ya kayıtlar silinir, ya da silinecek kayıt yoktur.
Her iki durumda da, Java tarafında “geri döndürülecek bir veri” yoktur.
 */
}
