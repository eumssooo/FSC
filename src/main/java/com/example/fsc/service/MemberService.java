package com.example.fsc.service;

import com.example.fsc.dto.memberDTO.MemberDTO;
import com.example.fsc.entity.memberEntity.MemberEntity;
import com.example.fsc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    //회원가입
    public void signup(MemberEntity memberEntity){
        //중복 이메일 확인
        if(isEmailUnique(memberEntity.getEmail())){
            //비밀번호 암호화(sha-256)
            String encryptedPassword = passwordEncoder.encode(memberEntity.getPassword());

            //회원 정보 저장
            MemberEntity encryptedEntity = new MemberEntity();
            encryptedEntity.setEmail(memberEntity.getEmail());
            encryptedEntity.setPassword(encryptedPassword);

            memberRepository.save(memberEntity);
        }else {

        }

    }

    public boolean isEmailUnique(String email){
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        return memberEntity==null;
    }
}
