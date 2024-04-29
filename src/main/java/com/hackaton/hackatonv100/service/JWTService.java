package com.hackaton.hackatonv100.service;

import com.hackaton.hackatonv100.utils.Constant;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
@AllArgsConstructor
@Log4j
public class JWTService {

    private static final String KEY = "i2h82hgh289gh2389gh2398gh249gh298gh13ogjig23g32";
    private static final long TIME_EXPIRATION = 3_600_000 * 24;

    public String generateToken(String subject) {
        String jwt = Jwts.builder()
                .signWith(signedKey())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TIME_EXPIRATION))
                .subject(subject)
                .compact();

        log.info("Created token for subject " + subject + ": " + jwt);
        return jwt;
    }

    public String extractSubject(HttpServletRequest request) {
        return extractSubject(request.getHeader(Constant.AUTHORIZATION));
    }

    public String extractSubject(String token) {
        if(token == null) {
            return null;
        }
        if(token.startsWith(Constant.BEARER)) {
            token = token.substring(Constant.BEARER.length());
        }
        return Jwts.parser()
                .setSigningKey(signedKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Key signedKey() {
        byte[] bytes = Base64.getDecoder().decode(KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

}