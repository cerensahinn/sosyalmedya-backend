package com.cerensahin.sosyalmedya.baslat;

import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.entity.Rol;
import com.cerensahin.sosyalmedya.repository.KullaniciRepository;
import com.cerensahin.sosyalmedya.ortak.SifreGizleme;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class BaslangicVerisi implements ApplicationRunner {

    private final KullaniciRepository kullaniciRepository;

    public BaslangicVerisi(KullaniciRepository kullaniciRepository) {
        this.kullaniciRepository = kullaniciRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        final String adminEmail = "admin@example.com";
        final String adminUsername = "admin";

        boolean emailYok = kullaniciRepository.findByEmail(adminEmail).isEmpty();
        boolean usernameYok = !kullaniciRepository.existsByKullaniciAdi(adminUsername);

        if (emailYok && usernameYok) {
            Kullanici admin = new Kullanici();
            admin.setAd("Admin");
            admin.setSoyad("Kullanıcı");
            admin.setKullaniciAdi(adminUsername);
            admin.setEmail(adminEmail.toLowerCase());
            admin.setSifreHash(SifreGizleme.sha256("Admin123!"));
            admin.setRol(Rol.ADMIN); // Enum olarak atanıyor

            kullaniciRepository.save(admin);
            System.out.println(">> [INIT] ADMIN oluşturuldu: " + adminEmail + " / Admin123!");
        } else {
            System.out.println(">> [INIT] ADMIN zaten var: " + adminEmail);
        }
    }
}
