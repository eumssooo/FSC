//package com.example.fsc.security;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.RequestHeader;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@RequiredArgsConstructor
//public class JwtAuthenticationFilter extends GenericFilterBean {
//    private final JwtTokenProvider tokenProvider;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//    String token = tokenProvider.resolveToken((HttpServletRequest) request);
//        System.out.println(token);
//        if(token!=null && tokenProvider.validate(token)){
//            Authentication authentication = tokenProvider.getAutnetication(token);
//        }
//    }
//
//}
