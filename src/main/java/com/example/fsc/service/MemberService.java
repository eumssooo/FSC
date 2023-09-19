package com.example.fsc.service;

import com.example.fsc.dto.memberDTO.MemberDTO;
import com.example.fsc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void signup(MemberDTO memberDTO){

    }
}
