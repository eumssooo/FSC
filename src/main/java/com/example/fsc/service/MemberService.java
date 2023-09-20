package com.example.fsc.service;

import com.example.fsc.dto.memberDTO.TokenDTO;
import com.example.fsc.entity.memberEntity.MemberEntity;
import com.example.fsc.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final TokenDTO tokenDTO;
    //private final PasswordEncoder passwordEncoder;


    //회원가입
    public ResponseEntity<Map<String, String>> signup(MemberEntity memberEntity){
        Map<String,String> result = new HashMap<>();
        //중복 이메일 확인
        if(isEmailUnique(memberEntity.getEmail())){
            //비밀번호 암호화(sha-256)
            //String encryptedPassword = passwordEncoder.encode(memberEntity.getPassword());

            //회원 정보 저장
            MemberEntity registerMemberEntity = new MemberEntity();
            registerMemberEntity.setEmail(memberEntity.getEmail());
            registerMemberEntity.setPassword(memberEntity.getPassword());


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

    public String generateToken(MemberEntity memberEntity){
        System.out.println(memberEntity.getEmailId());
        String jwt = Jwts.builder()
                .setSubject("loginUser")
                .claim("emailId",memberEntity.getEmailId())
                .claim("email",memberEntity.getEmail())
                .compact();
        return jwt;
    }

    public ResponseEntity<Map<String, String>> login(MemberEntity memberEntity, HttpServletResponse httpServletResponse) {
        //아이디 비밀번호 확인하기
        if(findByEmailAndPassword(memberEntity.getEmail(),memberEntity.getPassword())
        ){
            MemberEntity loginMemberEntity = setLoginMemberEntity(memberEntity.getEmail(),memberEntity.getPassword());
            //맞을때
            String jwt = generateToken(loginMemberEntity);
            tokenDTO.setMemberToken(jwt);
            httpServletResponse.addHeader("loginUser",jwt);
            Map<String,String > map = new HashMap<>();
            map.put("message","로그인이 성공적으로 완료되었습니다.");
            //showToken(tokenDTO);
            return ResponseEntity.status(200).body(map);
        }else {
            //아닐때
            System.out.println("로그인실패");
        }
        return null;
    }

    public boolean findByEmailAndPassword (String email, String password){
        MemberEntity memberEntity = memberRepository.findByEmailAndPassword(email,password);
        return memberEntity!= null;
    }

    public MemberEntity setLoginMemberEntity (String email, String password){
        MemberEntity memberEntity = memberRepository.findByEmailAndPassword(email,password);
        return memberEntity;
    }

    public String showToken(String header){
        System.out.println(Jwts.parser().parseClaimsJws(header).getBody().getSubject());
        return null;
//        String token = tokenDTO.getMemberToken();
//        System.out.println(token);
//        Claims claims = Jwts.parser()
//                .parseClaimsJwt(token)
//                .getBody();
//        System.out.println((claims.get("emailId")));

//        claims.get("emailId");
        //Long emailId = Long.valueOf(claims.get("emailId"));
//        Jwts.parser().parseClaimsJws(token).getBody().getSubject();
//        String email = (String) claims.get("email");
//        System.out.printf("email_id %d, email %s 님.",emailId,email);
    }

}
