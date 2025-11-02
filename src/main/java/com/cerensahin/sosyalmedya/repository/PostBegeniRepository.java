package com.cerensahin.sosyalmedya.repository;

import com.cerensahin.sosyalmedya.entity.PostBegeni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBegeniRepository extends JpaRepository<PostBegeni, Long> {

    boolean existsByGonderi_IdAndKullanici_Id(Long gonderiId, Long kullaniciId);
    //.. gönderi ve kullanıcı id'lerine göre beğeni var mı yok mu kontrol et
    // yani kullanıcı göndriyi beğenmiş mi diye bakar true/false döner
    //🎯Aynı kullanıcının aynı gönderiyi birden fazla beğenmesini engellemek

    long countByGonderi_Id(Long gonderiId);
    //.. gönderi id'sine göre o gönderinin kaç beğenisi olduğunu sayar ve döner
    //🎯Gönderi detayında “❤️ 56 kişi beğendi” bilgisini göstermek.

    void deleteByGonderi_IdAndKullanici_Id(Long gonderiId, Long kullaniciId);
    //.. gönderi ve kullanıcı id'lerine göre o beğeni kaydını siler
    //🎯Kullanıcı gönderinin beğenisini geri çektiğinde, o beğeni kaydını silmek için

    void deleteByGonderi_Id(Long gonderiId);
    //Belirli bir gönderiye ait tüm beğenileri sil
    //Gönderi silindiğinde, ona ait beğenilerin de silinmesi gerekir

}


