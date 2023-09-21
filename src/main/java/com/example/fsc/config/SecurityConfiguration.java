//package com.example.fsc.config;
//
////import com.example.fsc.security.JwtAuthenticationFilter;
//import com.example.fsc.security.JwtTokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//@Configuration
//@EnableWebSecurity
//@RequiredArgsConstructor
//public class SecurityConfiguration {
////    private final JwtTokenProvider jwtTokenProvider;
//    @Bean
//    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
////        http.headers().frameOptions().sameOrigin()
////                .and()
////                //.authorizeRequests()
////                //.antMatchers("/api/**").permitAll() // 로그인 페이지에 대한 접근 허용
////                //.anyRequest().authenticated() // 그 외 모든 요청은 인증이 필요
////                //.and()
////                .formLogin().disable()
////                //.loginPage("/login").permitAll().and()
////                //.logout().logoutUrl("/logout").permitAll().and()
////                .csrf().disable()
////                .httpBasic().disable()
////                .rememberMe().disable()
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
////                //.and().exceptionHandling().authenticationEntryPoint(new FailedPoint());
//
////        http.headers().frameOptions().sameOrigin()
////                .and()
////                .formLogin().disable()
////                .csrf().disable()
////                .httpBasic().disable()
////                .rememberMe().disable()
////                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http.build();
//
//        return  http.build();
//    }
//
////    @Bean
////    public PasswordEncoder passwordEncoder(){
////        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
////    }
//}
////
////class FailedPoint implements AuthenticationEntryPoint{
////
////    @Override
////    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
////        response.setContentType("application/json");
////        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
////        response.getWriter().write("{\"message\":\"Do not have permission.\"}");
////    }
////
////
////}
