package com.cerensahin.sosyalmedya.repository;

import com.cerensahin.sosyalmedya.entity.Yorum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YorumRepository extends JpaRepository<Yorum, Long> {
    Page<Yorum> findByGonderi_IdOrderByOlusturmaZamaniAsc(Long gonderiId, Pageable pageable);

    void deleteByGonderi_Id(Long gonderiId);

}
