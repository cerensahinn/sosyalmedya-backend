package com.cerensahin.sosyalmedya.repository;

import com.cerensahin.sosyalmedya.entity.Yorum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YorumRepository extends JpaRepository<Yorum, Long> {
    Page<Yorum> findByGonderi_IdOrderByOlusturmaZamaniAsc(Long gonderiId, Pageable pageable);
    //.. Id'li gönderiye ait yorumları, oluşturulma zamanına göre eski yeni (ASC) sırala ve sayfalı şekilde getir

    void deleteByGonderi_Id(Long gonderiId);
    //Yorum tablosunda gonderi_id = verilen id olan o gönderiye bağlı yorum kayıtlarını siler
    //🎯Gönderi silindiğinde o gönderiye ait yorumları da temizlemek için
}
