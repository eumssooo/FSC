package com.example.fsc.service;

import com.example.fsc.dto.memberDTO.Token;
import com.example.fsc.dto.memberDTO.ResponseToken;
import com.example.fsc.entity.memberEntity.MemberEntity;
import com.example.fsc.repository.MemberRepository;
import com.example.fsc.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    //회원가입
    public ResponseEntity<Map<String, String>> signup(Map<String, String> signup){
        Map<String, String> result = new HashMap<>();
        if(isEmailUnique(signup.get("email"))){
            MemberEntity loginEntity = new MemberEntity();
            loginEntity.setEmail(signup.get("email"));
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String encPassword = bCryptPasswordEncoder.encode(signup.get("password"));
            loginEntity.setPassword(encPassword);
            memberRepository.save(loginEntity);
            result.put("message", "회원가입이 완료되었습니다.");
            return ResponseEntity.status(200).body(result);
        }else {
            result.put("message", "회원가입에 실패하였습니다.");
            return ResponseEntity.status(200).body(result);
        }
    }

    public boolean isEmailUnique(String email){
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        return memberEntity==null;
    }

    public ResponseEntity<?> login(Map<String, String> login,HttpServletResponse httpServletResponse) {
        //아이디 비밀번호 확인하기
        if(findByEmail(login.get("email"))){
            if(findByPasswordCheck(login.get("email"),login.get("password"))){
                Long userId = memberRepository.findByEmail(login.get("email")).getEmailId();;

                MemberEntity userRoles = memberRepository.findByEmail(login.get("email"));
                Token token = jwtTokenProvider.create(userId, userRoles);

                ResponseToken responseToken = com.example.fsc.dto.memberDTO.ResponseToken.builder()
                        .accessToken(token.getAccessToken())
                        .accessTokenExpireDate(token.getAccessTokenExpireDate())
                        .email(login.get("email"))
                        .memberId(userId)
                        .message("로그인 성공")
                        .build();
                httpServletResponse.addHeader("loginUser",token.getAccessToken());

                return ResponseEntity.status(HttpStatus.OK).body(responseToken);
            }else {
                Map<String, String> result = new HashMap<>();
                result.put("message", "로그인 실패");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
            }
        } else {
            Map<String, String> result = new HashMap<>();
            result.put("message", "로그인 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }

    public boolean findByEmail (String email){
        MemberEntity loginEntity = memberRepository.findByEmail(email);
        return loginEntity != null;
    }

    public boolean findByPasswordCheck(String email,String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String entityPassword = memberRepository.findByEmail(email).getPassword();
        return bCryptPasswordEncoder.matches(password,entityPassword);
    }

    public boolean findByEmailAndPassword (String email, String password){
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        if(memberEntity!=null){
            return passwordEncoder.matches(password,memberEntity.getPassword());
        }
        return false;
    }


    public Map<String, Object> showToken(String token){

        Claims claims = Jwts.parser()
               .parseClaimsJwt(token)
               .getBody();
        Long emailId = claims.get("emailId", Long.class);
        String email = claims.get("email", String.class);
        System.out.println("emailId : " +emailId);
        System.out.println("email : "+email);
        Map<String ,Object> loginInfo = new HashMap<>();
        loginInfo.put("emailId",emailId);
        loginInfo.put("email",email);

        return loginInfo;
    }


    public ResponseEntity<Map<String, String>> logout(MemberEntity memberEntity, HttpServletResponse httpServletResponse, String token) {
        System.out.println(token);

        httpServletResponse.setHeader(null,null);
        Map<String,String > map = new HashMap<>();
        map.put("message","로그아웃 되었습니다.");
        return ResponseEntity.status(200).body(map);
    }
}
