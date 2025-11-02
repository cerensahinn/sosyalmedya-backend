package com.cerensahin.sosyalmedya.ortak.handler;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;

import org.springframework.core.annotation.Order;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice 
@Order(100)

public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)

    @ResponseStatus(HttpStatus.BAD_REQUEST)

    public Map<String, Object> handleIllegalArgument(IllegalArgumentException ex) {

        log.warn("400 IllegalArgumentException: {}", ex.getMessage());

        return Map.of(
                "mesaj", ex.getMessage(),
                "detaySinif", ex.getClass().getName() 
        );

    }

    @ExceptionHandler(Exception.class)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

    public Map<String, Object> handleGeneral(Exception ex) {

        log.error("500 Internal Server Error", ex);

        return Map.of(
                "mesaj", "Beklenmeyen bir hata oluştu.",
                "detaySinif", ex.getClass().getName(),
                "detayMesaj", ex.getMessage()
        );

    }
}
