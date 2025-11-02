package com.cerensahin.sosyalmedya.repository;

import com.cerensahin.sosyalmedya.entity.PostBegeni;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBegeniRepository extends JpaRepository<PostBegeni, Long> {

    boolean existsByGonderi_IdAndKullanici_Id(Long gonderiId, Long kullaniciId);

    long countByGonderi_Id(Long gonderiId);

    void deleteByGonderi_IdAndKullanici_Id(Long gonderiId, Long kullaniciId);

    void deleteByGonderi_Id(Long gonderiId);

}

