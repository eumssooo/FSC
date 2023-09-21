package com.example.fsc.service;

import com.example.fsc.entity.memberEntity.MemberEntity;
import com.example.fsc.repository.MemberRepository;
import com.example.fsc.security.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
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
    //private final TokenDTO tokenDTO;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    //회원가입
    public ResponseEntity<Map<String, String>> signup(MemberEntity memberEntity){
        Map<String,String> result = new HashMap<>();
        //중복 이메일 확인
        if(isEmailUnique(memberEntity.getEmail())){
            //비밀번호 암호화(sha-256)
            String encryptedPassword = passwordEncoder.encode(memberEntity.getPassword());

            //회원 정보 저장
            MemberEntity registerMemberEntity = new MemberEntity();
            registerMemberEntity.setEmail(memberEntity.getEmail());
            registerMemberEntity.setPassword(encryptedPassword);


            memberRepository.save(registerMemberEntity);
            result.put("message","회원가입이 완료되었습니다.");
            return ResponseEntity.status(200).body(result);
        }else{
            result.put("message","이미 등록 된 아이디입니다..");
            return ResponseEntity.status(200).body(result);
        }

    }

    public boolean isEmailUnique(String email){
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        return memberEntity==null;
    }

//    public String generateToken(MemberEntity memberEntity){
//        System.out.println(memberEntity.getEmailId());
//        String jwt = Jwts.builder()
//                .setSubject("loginUser")
//                .claim("emailId",memberEntity.getEmailId())
//                .claim("email",memberEntity.getEmail())
//                .compact();
//        return jwt;
//    }

    public ResponseEntity<Map<String, String>> login(MemberEntity memberEntity, HttpServletResponse httpServletResponse) {
        //아이디 비밀번호 확인하기
        if(findByEmailAndPassword(memberEntity.getEmail(),memberEntity.getPassword())
        ){  //로그인 성공했을 때 memberEntity 다시 정의
            MemberEntity loginMemberEntity = setLoginMemberEntity(memberEntity.getEmail());
            // 토큰 생성
            String jwt = jwtTokenProvider.create(loginMemberEntity.getEmailId(),loginMemberEntity.getEmail());
            //tokenDTO.setToken(jwt);
            httpServletResponse.addHeader("loginUser",jwt);
            Map<String,String > map = new HashMap<>();
            map.put("message","로그인이 성공적으로 완료되었습니다.");
            showToken(jwt);
            return ResponseEntity.status(200).body(map);
        }else {
            //아닐때
            System.out.println("로그인실패");
        }
        System.out.println(4444);
        return null;
    }

    public boolean findByEmailAndPassword (String email, String password){
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        if(memberEntity!=null){
            return passwordEncoder.matches(password,memberEntity.getPassword());
        }
        return false;
    }

    public MemberEntity setLoginMemberEntity (String email){
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        return memberEntity;
    }

    public Map<String, String> showToken(String token){

        Claims claims = Jwts.parser()
               .parseClaimsJwt(token)
               .getBody();
        Long emailId = claims.get("emailId", Long.class); // 올바른 방법
        String email = (String) claims.get("email");
        System.out.println("emailId : " +emailId);
        System.out.println("email : "+email);
        Map<String ,String> loginInfo = new HashMap<>();
        loginInfo.put("emailId",email);
        loginInfo.put("email",email);

        return loginInfo;
    }

    public ResponseEntity<Map<String, String>> logout(MemberEntity memberEntity, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader(null,null);
        Map<String,String > map = new HashMap<>();
        map.put("message","로그아웃 되었습니다.");
        return ResponseEntity.status(200).body(map);


    }
}
