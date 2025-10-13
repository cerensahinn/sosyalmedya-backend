package com.cerensahin.sosyalmedya.ortak.handler;

import com.cerensahin.sosyalmedya.controller.YorumController;
import com.cerensahin.sosyalmedya.ortak.exception.yorum.YorumBulunamadiException;
import com.cerensahin.sosyalmedya.ortak.exception.yorum.YorumEkleGonderiBulunamadiException;
import com.cerensahin.sosyalmedya.ortak.exception.yorum.YorumSilmeYetkiYokException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestControllerAdvice(assignableTypes = { YorumController.class })
public class YorumExceptionHandler {

    @ExceptionHandler(YorumBulunamadiException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public Map<String, Object> handleNotFound(YorumBulunamadiException ex) {
        return Map.of(
                "mesaj", ex.getMessage(),
                "kod", "YORUM_404",
                "yorumId", ex.getYorumId()
        );
    }

    @ExceptionHandler(YorumSilmeYetkiYokException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN) // 403
    public Map<String, Object> handleForbidden(YorumSilmeYetkiYokException ex) {
        return Map.of(
                "mesaj", ex.getMessage(),
                "kod", "YORUM_403",
                "yorumId", ex.getYorumId()
        );
    }

    @ExceptionHandler(YorumEkleGonderiBulunamadiException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // 404
    public Map<String, Object> handleAddPostNotFound(YorumEkleGonderiBulunamadiException ex) {
        return Map.of(
                "mesaj", ex.getMessage(),
                "kod", "YORUM_EKLE_404",
                "gonderiId", ex.getGonderiId()
        );
    }
}
