package com.example.fsc.service;

import com.example.fsc.entity.memberEntity.MemberEntity;
import com.example.fsc.repository.MemberRepository;
import com.example.fsc.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    //    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    }

    //회원가입
    public ResponseEntity<Map<String, String>> signup(MemberEntity memberEntity) {
        Map<String, String> result = new HashMap<>();
        //중복 이메일 확인
        if (isEmailUnique(memberEntity.getEmail())) {
            //비밀번호 암호화(sha-256)
            //String encryptedPassword = passwordEncoder.encode(memberEntity.getPassword());
            String password = memberEntity.getPassword();
            //회원 정보 저장
            MemberEntity registerMemberEntity = new MemberEntity();
            registerMemberEntity.setEmail(memberEntity.getEmail());
            registerMemberEntity.setPassword(password);


            memberRepository.save(registerMemberEntity);
            result.put("message", "회원가입이 완료되었습니다.");
            return ResponseEntity.status(200).body(result);
        } else {
            result.put("message", "이미 등록 된 아이디입니다..");
            return ResponseEntity.status(200).body(result);
        }

    }

    public boolean isEmailUnique(String email) {
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        return memberEntity == null;
    }

    public ResponseEntity<Map<String, String>> login(MemberEntity memberEntity, HttpServletResponse httpServletResponse) {
        //아이디 비밀번호 확인하기
        if (findByEmailAndPassword(memberEntity.getEmail(), memberEntity.getPassword())
        ) {  //로그인 성공했을 때 memberEntity 다시 정의
            MemberEntity loginMemberEntity = setLoginMemberEntity(memberEntity.getEmail());
            // 토큰 생성
            String jwt = jwtTokenProvider.create(loginMemberEntity.getEmailId(), loginMemberEntity.getEmail());
            //tokenDTO.setToken(jwt);
            httpServletResponse.addHeader("loginUser", jwt);
            Map<String, String> map = new HashMap<>();
            map.put("message", "로그인이 성공적으로 완료되었습니다.");
            return ResponseEntity.status(200).body(map);
        } else {
            //아닐때
            System.out.println("로그인실패");
        }
        return null;
    }

    public boolean findByEmailAndPassword(String email, String password) {
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        if (memberEntity != null) {
            return password.equals(memberEntity.getPassword());
        }
        return false;
    }

    public MemberEntity setLoginMemberEntity(String email) {
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        return memberEntity;
    }

    public Map<String, String> showToken(String token) {
        if (token != null) {
            Claims claims = Jwts.parser()
                    .parseClaimsJwt(token)
                    .getBody();
            Long emailId = claims.get("emailId", Long.class);
            String email = claims.get("email", String.class);
            System.out.println("emailId : " + emailId);
            System.out.println("email : " + email);
            Map<String, String> loginInfo = new HashMap<>();
            loginInfo.put("emailId", String.valueOf(emailId));
            loginInfo.put("email", email);
            loginInfo.put("emailId", email);
            loginInfo.put("email", email);
            return loginInfo;
        } else {
            return null;
        }
    }

    public ResponseEntity<Map<String, String>> logout(MemberEntity memberEntity, HttpServletResponse httpServletResponse, String token) {
        Optional<String > tokenOptional = Optional.ofNullable(token);
        if(tokenOptional.isPresent()){
//            String exToken = tokenOptional.get();
//            System.out.println(exToken);
            httpServletResponse.setHeader(null,null);
            Map<String,String > map = new HashMap<>();
            map.put("message","로그아웃 되었습니다.");
            return ResponseEntity.status(200).body(map);
        }else{
//            System.out.println("로그인 하지 않았습니다.");
            Map<String,String > map = new HashMap<>();
            map.put("message","로그인 한 사용자가 아닙니다.");
            return ResponseEntity.status(200).body(map);
        }
    }
}