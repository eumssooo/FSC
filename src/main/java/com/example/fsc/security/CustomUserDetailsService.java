package com.example.fsc.security;

import com.example.fsc.dto.memberDTO.MemberDetails;
import com.example.fsc.entity.memberEntity.MemberEntity;
import com.example.fsc.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByEmail(username);
        MemberDetails memberDetails = new MemberDetails();
        memberDetails.setID(String.valueOf(memberEntity.getEmailId()));
        memberDetails.setNAME(memberEntity.getEmail());
        memberDetails.setAUTHORITY("ROLE_USER");

        return memberDetails;
    }
}
