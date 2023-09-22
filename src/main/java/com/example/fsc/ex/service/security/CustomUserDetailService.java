package com.example.fsc.ex.service.security;

import com.example.fsc.ex.exception.NotFoundException;
import com.example.fsc.ex.roles.Roles;
import com.example.fsc.ex.userDetails.CustomUserDetails;
import com.example.fsc.ex.userPrincipal.UserPrincipal;
import com.example.fsc.ex.userPrincipal.UserPrincipalRepository;
import com.example.fsc.ex.userPrincipal.UserPrincipalRoles;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Primary
@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserPrincipalRepository userPrincipalRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String userPk) throws UsernameNotFoundException {
        UserPrincipal userPrincipal = userPrincipalRepository.findByuserPrincipalId(Long.valueOf(userPk));

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUserId(userPrincipal.getUserPrincipalId());
        customUserDetails.setEmail(userPrincipal.getEmail());
        customUserDetails.setAuthorities("ROLE_USER");

        return customUserDetails;
    }
}
