package com.example.fsc.ex.config;

import com.example.fsc.ex.dto.jwt.Token;
import com.example.fsc.ex.service.security.CustomUserDetailService;
import com.example.fsc.ex.userPrincipal.UserPrincipal;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenProvider {
    private String ROLES = "roles";
    private final String secretKey = Base64.getEncoder()
            .encodeToString("super-coding".getBytes());

    private long tokenValidMillisecond = 1000L * 60 * 60; // 1시간

    private final CustomUserDetailService customUserDetailService;

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }

    public Token createToken(Long userPk, UserPrincipal roles) {
        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(userPk));
        claims.put(ROLES, roles);
        Date now = new Date();
        Date accessTokenExpiryCalc = new Date(now.getTime() + tokenValidMillisecond);
        String accessToken = Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(accessTokenExpiryCalc)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
        return Token.builder()
                .grantType("bearer")
                .accessToken(accessToken)
                .accessTokenExpireDate(accessTokenExpiryCalc)
                .build();

//        return Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(now)
//                .setExpiration(new Date(now.getTime() + tokenValidMillisecond))
//                .signWith(SignatureAlgorithm.HS256, secretKey)
//                .compact();
    }

    public boolean validateToken(String jwtToken) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwtToken)
                    .getBody();
            Date now = new Date();
            return claims.getExpiration()
                    .after(now);
        } catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String jwtToken) {
        Claims claims = parseClaims(jwtToken);
        UserDetails userDetails = customUserDetailService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserEmail(String jwtToken) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwtToken)
                .getBody()
                .getSubject();
    }

    public Claims parseClaims(String token){
        try{
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
