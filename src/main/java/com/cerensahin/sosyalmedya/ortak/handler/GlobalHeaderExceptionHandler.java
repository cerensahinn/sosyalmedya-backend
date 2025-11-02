package com.cerensahin.sosyalmedya.ortak.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalHeaderExceptionHandler {

    @ExceptionHandler(MissingRequestHeaderException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED) 
    public Map<String, Object> missingHeader(MissingRequestHeaderException ex) {
        String headerName = ex.getHeaderName(); 

        if ("Authorization".equalsIgnoreCase(headerName)) {
            return Map.of(
                    "mesaj", "Authorization header zorunludur.",
                    "kod", "TOKEN_HEADER_MISSING_401"
            );
        }

        return Map.of(
                "mesaj", "Zorunlu header eksik: " + headerName,
                "kod", "HEADER_MISSING_401"
        );
    }
}
