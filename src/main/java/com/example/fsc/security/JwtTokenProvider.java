package com.example.fsc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtTokenProvider {


    public String create(Long emailId, String email){
        //한시간 만료 시간 설정
        Date expiredDate = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        String jwt = Jwts.builder()
                .setSubject("loginUser")
                .claim("emailId",emailId)
                .claim("email",email)
                .setIssuedAt(new Date()).setExpiration(expiredDate)
                .compact();
        return jwt;
    }

    public String validate(String jwt){
        Claims claims = null;

        try {
            claims = Jwts.parser()
                    .parseClaimsJws(jwt).getBody();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

        return claims.getSubject();
    }

}
