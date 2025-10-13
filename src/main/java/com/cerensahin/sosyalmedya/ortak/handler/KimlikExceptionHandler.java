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
    public Map<String, Object> badCreds(GecersizKimlikBilgileriException ex) {
        return Map.of("mesaj", ex.getMessage(), "kod", "AUTH_401");
    }
}
