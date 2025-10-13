package com.cerensahin.sosyalmedya.config;

import com.cerensahin.sosyalmedya.entity.ErisimTokeni;
import com.cerensahin.sosyalmedya.entity.Kullanici;
import com.cerensahin.sosyalmedya.ortak.exception.kimlik.TokenGecersizException;
import com.cerensahin.sosyalmedya.ortak.exception.kimlik.TokenSuresiDolmusException;
import com.cerensahin.sosyalmedya.repository.ErisimTokeniRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    private final ErisimTokeniRepository erisimTokeniRepository;

    public TokenInterceptor(ErisimTokeniRepository erisimTokeniRepository) {
        this.erisimTokeniRepository = erisimTokeniRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String path = request.getRequestURI();
        if (path.startsWith("/api/auth")) return true;

        String auth = request.getHeader("Authorization");
        if (auth == null || !auth.startsWith("Token ")) {
            throw new TokenGecersizException(null);
        }
        String deger = auth.substring("Token ".length()).trim();

        ErisimTokeni token = erisimTokeniRepository.findByDegerAndAktifTrue(deger)
                .orElseThrow(() -> new TokenGecersizException(deger));

        if (token.getSonKullanmaZamani() != null &&
                token.getSonKullanmaZamani().isBefore(LocalDateTime.now())) {
            throw new TokenSuresiDolmusException(token.getSonKullanmaZamani());
        }

        Kullanici aktif = token.getKullanici();
        request.setAttribute("aktifKullanici", aktif);
        return true;
    }
}
