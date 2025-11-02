package com.cerensahin.sosyalmedya.ortak.handler;

import com.cerensahin.sosyalmedya.controller.GonderiController;
import com.cerensahin.sosyalmedya.ortak.exception.gonderi.BegeniBulunamadiException;
import com.cerensahin.sosyalmedya.ortak.exception.gonderi.BegeniZatenVarException;
import com.cerensahin.sosyalmedya.ortak.exception.gonderi.GonderiBulunamadiException;
import com.cerensahin.sosyalmedya.ortak.exception.gonderi.GonderiIslemYetkisizException;
import com.cerensahin.sosyalmedya.ortak.exception.medya.MedyaCokBuyukException;
import com.cerensahin.sosyalmedya.ortak.exception.medya.MedyaGecersizException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestControllerAdvice(assignableTypes = { GonderiController.class })

public class GonderiExceptionHandler {

    @ExceptionHandler(GonderiBulunamadiException.class)

    @ResponseStatus(HttpStatus.NOT_FOUND)

    public Map<String,Object> postNotFound(GonderiBulunamadiException ex){
        return Map.of("mesaj", ex.getMessage(), "kod", "GONDERI_404", "gonderiId", ex.getGonderiId());
    }

    @ExceptionHandler(GonderiIslemYetkisizException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)

    public Map<String,Object> postForbidden(GonderiIslemYetkisizException ex){
        return Map.of("mesaj", ex.getMessage(), "kod", "GONDERI_403", "gonderiId", ex.getGonderiId());
    }

    @ExceptionHandler(BegeniZatenVarException.class)
    @ResponseStatus(HttpStatus.CONFLICT)

    public Map<String,Object> likeExists(BegeniZatenVarException ex){
        return Map.of("mesaj","Bu gönderi zaten beğenilmiş","kod","BEGENI_409","gonderiId",ex.getGonderiId());
    }

    @ExceptionHandler(BegeniBulunamadiException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,Object> likeNotFound(BegeniBulunamadiException ex){
        return Map.of("mesaj","Beğeni bulunamadı","kod","BEGENI_404","gonderiId",ex.getGonderiId());
    }

    @ExceptionHandler(MedyaGecersizException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> mediaInvalid(MedyaGecersizException ex){
        return Map.of("mesaj", ex.getMessage(), "kod", "MEDYA_400", "contentType", ex.getContentType());
    }

    @ExceptionHandler(MedyaCokBuyukException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)

    public Map<String,Object> mediaTooLarge(MedyaCokBuyukException ex){
        return Map.of("mesaj", ex.getMessage(), "kod", "MEDYA_413", "size", ex.getSize());
    }
}
