package com.example.fsc.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.tokens.Token;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    //private final CustomUserDetailsService customUserDetailsService;

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

    public boolean validate(String token){

        try {
            Jws<Claims> claimsJws = Jwts.parser().parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("loginUser");
    }

//    public Authentication getAutnetication(String token) {
//        Claims claims = parseClaims(token);
//        UserDetails userDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
//        return  null;
//    }

    private Claims parseClaims(String token) {
        try{
            return Jwts.parser().parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
