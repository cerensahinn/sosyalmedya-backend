package com.cerensahin.sosyalmedya.ortak.handler;

import com.cerensahin.sosyalmedya.controller.KimlikController;
import com.cerensahin.sosyalmedya.ortak.exception.kimlik.GecersizKimlikBilgileriException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice(assignableTypes = { KimlikController.class })
public class KimlikExceptionHandler {

    @ExceptionHandler(GecersizKimlikBilgileriException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    //Bu hata oluştuğunda kullanıcı giriş yapmamış veya geçerli bir token sunmamış demektir.
    //O yüzden istemciye 401 Unauthorized gönder.
    //401 Unauthorized → “Kimlik doğrulama başarısız” anlamına gelir.
    public Map<String, Object> badCreds(GecersizKimlikBilgileriException ex) {
        return Map.of("mesaj", ex.getMessage(), "kod", "AUTH_401");
    }
}
