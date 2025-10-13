package com.cerensahin.sosyalmedya.ortak.handler;

import com.cerensahin.sosyalmedya.controller.KullaniciController;
import com.cerensahin.sosyalmedya.ortak.exception.kullanici.EmailZatenKayitliException;
import com.cerensahin.sosyalmedya.ortak.exception.kullanici.EskiSifreHataliException;
import com.cerensahin.sosyalmedya.ortak.exception.kullanici.KullaniciBulunamadiException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;        // ✅ EKLENDİ
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice(assignableTypes = {KullaniciController.class})
public class KullaniciExceptionHandler {

    @ExceptionHandler(KullaniciBulunamadiException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Object> notFound(KullaniciBulunamadiException ex) {
        return Map.of(
                "mesaj", ex.getMessage(),
                "kod", "KULLANICI_404",
                "kullaniciId", ex.getKullaniciId()
        );
    }

    @ExceptionHandler(EmailZatenKayitliException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> emailExists(EmailZatenKayitliException ex) {
        return Map.of(
                "mesaj", ex.getMessage(),
                "kod", "KULLANICI_409",
                "email", ex.getEmail()
        );
    }

    @ExceptionHandler(EskiSifreHataliException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> wrongOldPwd(EskiSifreHataliException ex) {
        return Map.of(
                "mesaj", ex.getMessage(),
                "kod", "KULLANICI_SIFRE_400"
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, Object> uniqueViolation(DataIntegrityViolationException ex) {
        String detay = ex.getMostSpecificCause() != null
                ? ex.getMostSpecificCause().getMessage()
                : ex.getMessage();
        return Map.of(
                "mesaj", "Benzersiz alanlardan biri zaten kayıtlı.",
                "kod", "KULLANICI_409_CONSTRAINT",
                "detay", detay
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public @ResponseBody ResponseEntity<Map<String, Object>> illegalArg(IllegalArgumentException ex) {
        boolean adminMesaj = ex.getMessage() != null
                && ex.getMessage().toLowerCase().contains("admin rolü gerekir");

        HttpStatus status = adminMesaj ? HttpStatus.FORBIDDEN : HttpStatus.BAD_REQUEST;

        Map<String, Object> body = Map.of(
                "mesaj", ex.getMessage(),
                "kod", adminMesaj ? "YETKI_403" : "GECERSIZ_ISTEK_400"
        );

        return new ResponseEntity<>(body, status);
    }
}
