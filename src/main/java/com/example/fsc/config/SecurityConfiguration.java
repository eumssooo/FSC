 package com.example.fsc.config;

 import lombok.RequiredArgsConstructor;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.security.config.annotation.web.builders.HttpSecurity;
 import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
 import org.springframework.security.config.http.SessionCreationPolicy;
 import org.springframework.security.crypto.factory.PasswordEncoderFactories;
 import org.springframework.security.crypto.password.PasswordEncoder;
 import org.springframework.security.web.SecurityFilterChain;

 @Configuration
 @EnableWebSecurity
 @RequiredArgsConstructor
 public class SecurityConfiguration {
     @Bean
     protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
         http.headers().frameOptions().sameOrigin()
                 .and()
                 .formLogin().disable()
                 .csrf().disable()
                 .httpBasic().disable()
                 .rememberMe().disable()
                 .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
         return  http.build();

     }
     @Bean
     public PasswordEncoder passwordEncoder(){
         return PasswordEncoderFactories.createDelegatingPasswordEncoder();
     }
 }
