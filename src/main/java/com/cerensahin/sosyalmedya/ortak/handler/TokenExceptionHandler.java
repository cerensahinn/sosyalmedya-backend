package com.cerensahin.sosyalmedya.ortak.handler;

import com.cerensahin.sosyalmedya.ortak.exception.kimlik.TokenGecersizException;
import com.cerensahin.sosyalmedya.ortak.exception.kimlik.TokenSuresiDolmusException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice
public class TokenExceptionHandler {

    @ExceptionHandler(TokenGecersizException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String,Object> badToken(TokenGecersizException ex){
        return Map.of("mesaj", ex.getMessage(), "kod", "TOKEN_401", "token", ex.getToken());
    }

    @ExceptionHandler(TokenSuresiDolmusException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String,Object> expired(TokenSuresiDolmusException ex){
        return Map.of("mesaj", ex.getMessage(), "kod", "TOKEN_EXPIRED_401",
                "sonKullanim", ex.getSonKullanim() == null ? null : ex.getSonKullanim().toString());
    }
}
