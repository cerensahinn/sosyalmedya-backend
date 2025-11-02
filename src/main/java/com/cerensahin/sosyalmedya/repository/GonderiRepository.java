package com.cerensahin.sosyalmedya.repository;

import com.cerensahin.sosyalmedya.entity.Gonderi;
import org.springframework.data.domain.Page; 
import org.springframework.data.domain.Pageable; 
import org.springframework.data.jpa.repository.JpaRepository;

public interface GonderiRepository extends JpaRepository<Gonderi, Long> {
    Page<Gonderi> findByKullanici_IdOrderByOlusturmaZamaniDesc(Long kullaniciId, Pageable pageable);

    Page<Gonderi> findAllByOrderByOlusturmaZamaniDesc(Pageable pageable);

    void deleteByKullanici_Id(Long kullaniciId);

}
