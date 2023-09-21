package com.example.fsc.security;

import com.example.fsc.dto.memberDTO.MemberDetails;
import com.example.fsc.dto.memberDTO.Token;
import com.example.fsc.entity.memberEntity.MemberEntity;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final CustomUserDetailsService customUserDetailsService;


    private String ROLES = "roles";

//    @PostConstruct
//    protected void init(){
//        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
//    }

    public Token create(Long userPk, MemberEntity roles){
        //한시간 만료 시간 설정
        Claims claims = Jwts.claims().setSubject(String.valueOf(userPk));
        claims.put(ROLES,roles);
        Date now = new Date();
        Date accessTokenExpiryCalc = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(accessTokenExpiryCalc)
                .compact();

        return Token.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .accessTokenExpireDate(accessTokenExpiryCalc)
                .build();
    }

    public boolean validate(String token){
        try {
            System.out.println(1111);
            Jws<Claims> claimsJws = Jwts.parserBuilder().build().parseClaimsJws(token);
            System.out.println(2222);
            return !claimsJws.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);
        UserDetails memberDetails = customUserDetailsService.loadUserByUsername(claims.getSubject());
        return  new UsernamePasswordAuthenticationToken(memberDetails,"",memberDetails.getAuthorities());
    }

    private Claims parseClaims(String token) {
        try{
            return Jwts.parser().parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
    public String getUserPk(String token){
        return Jwts.parser().parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        return request.getHeader("loginUser");
    }

}
