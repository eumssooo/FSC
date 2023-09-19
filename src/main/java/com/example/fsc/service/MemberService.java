package com.example.fsc.service;

import com.example.fsc.dto.memberDTO.MemberDTO;
import com.example.fsc.entity.memberEntity.MemberEntity;
import com.example.fsc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;

    //회원가입
    public void signup(MemberEntity memberEntity){
        //중복 이메일 확인
        if(isEmailUnique(memberEntity.getEmail())){
            memberRepository.save(memberEntity);
        }else {

        }

    }

    public boolean isEmailUnique(String email){
        MemberEntity memberEntity = memberRepository.findByEmail(email);
        return memberEntity==null;
    }
}
