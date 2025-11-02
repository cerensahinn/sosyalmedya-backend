package com.cerensahin.sosyalmedya.repository;

import com.cerensahin.sosyalmedya.entity.ErisimTokeni;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ErisimTokeniRepository extends JpaRepository<ErisimTokeni, Long> {

    Optional<ErisimTokeni> findByDegerAndAktifTrue(String deger);

    Optional<ErisimTokeni> findTopByKullanici_IdAndAktifTrueOrderByOlusturmaZamaniDesc(Long kullanici_id);

    void deleteByKullanici_Id(Long kullaniciId);

}
