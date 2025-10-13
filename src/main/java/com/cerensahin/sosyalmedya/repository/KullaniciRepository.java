package com.cerensahin.sosyalmedya.repository;

import com.cerensahin.sosyalmedya.entity.Kullanici;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KullaniciRepository extends JpaRepository<Kullanici, Long> {

    Optional<Kullanici> findByEmail(String email);
    boolean existsByEmail(String email);

    // ✅ kullanıcı adı sorguları
    Optional<Kullanici> findByKullaniciAdi(String kullaniciAdi);
    boolean existsByKullaniciAdi(String kullaniciAdi);
}
