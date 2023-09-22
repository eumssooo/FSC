package com.example.fsc.ex.service.security;

import com.example.fsc.ex.config.TokenProvider;
import com.example.fsc.ex.dto.auth.Login;
import com.example.fsc.ex.dto.auth.SignUp;
import com.example.fsc.ex.dto.jwt.ResponseToken;
import com.example.fsc.ex.dto.jwt.Token;
import com.example.fsc.ex.exception.NotAcceptException;
import com.example.fsc.ex.roles.RolesRepository;
import com.example.fsc.ex.userPrincipal.UserPrincipal;
import com.example.fsc.ex.userPrincipal.UserPrincipalRepository;
//import com.example.fsc.ex.userPrincipal.UserPrincipalRolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider jwtTokenProvider;
    private final UserPrincipalRepository userPrincipalRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public boolean signUp(SignUp signUpRequest) {
        String email = signUpRequest.getEmail();
        String password = signUpRequest.getPassword();


        // 1. 아이디 중복 체크
        if (userPrincipalRepository.existsByEmail(email)) {
            return false;
        }
        //유저 정보 등록
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        userPrincipalRepository.save(userPrincipal);

        //유저 role 등록
        //roles가 있는지 없는지 부터 확인
//        Roles roles = rolesRepository.findByName("ROLE_USER")
//                .orElseThrow(() -> new NotFoundException("ROLE_USER를 찾을 수가 없습니다."));
//
//        //등록
//        userPrincipalRolesRepository.save(
//                UserPrincipalRoles.builder()
//                        .roles("ROLE_USER")
//                        .userPrincipal(userPrincipal)
//                        .build()
//        );
        return true;
    }


    public ResponseEntity<Map<String,String >> login(Login loginRequest, HttpServletResponse httpServletResponse) {

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        System.out.println(email+" "+password);

        if(findByEmail(email)){
            if(findByPasswordCheck(email,password)){
                try {
//                    Authentication authentication = authenticationManager.authenticate(
//                            new UsernamePasswordAuthenticationToken(email, password)
//                    );
//                    SecurityContextHolder.getContext()
//                            .setAuthentication(authentication);

//                    UserPrincipal userPrincipal = userPrincipalRepository.findByEmailFetchJoin(email)
//                            .orElseThrow(() -> new NotFoundException("UserPrincipal을 찾을 수 없습니다."));
//
//            if (userPrincipal.getEmail().isEmpty())return "UserPrincipal을 찾을 수 없습니다.";

//                    String roles = "ROLE_USER";
//                    List<String> roles = userPrincipal.getUserPrincipalRoles()
//                            .stream()
//                            .map(UserPrincipalRoles::getRoles)
//                            .map(Roles::getName)
//                            .collect(Collectors.toList());

                    Long userId = userPrincipalRepository.findByEmail(email).getUserPrincipalId();
                    UserPrincipal userRoles = userPrincipalRepository.findByEmail(email);
                    Token token = jwtTokenProvider.createToken(userId,userRoles);
//                    ResponseToken responseToken = ResponseToken.builder()
//                                    .accessToken(token.getAccessToken())
//                            .accessTokenExpireDate(token.getAccessTokenExpireDate())
//                            .email(email)
//                            .memberId(userId)
//                            .build();
                    Map<String,String > map = new HashMap<>();
                    map.put("message","로그인에 성공했습니다.");
                    httpServletResponse.setHeader("X-AUTH-TOKEN",token.getAccessToken());
                    return ResponseEntity.status(HttpStatus.OK).body(map);
                } catch (Exception e){
                    e.printStackTrace();
                    throw new NotAcceptException("로그인 할 수 없습니다.");
                }
            }else{
                Map<String, String> result = new HashMap<>();
                result.put("message", "로그인 실패");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
            }
        }else {
            Map<String, String> result = new HashMap<>();
            result.put("message", "로그인 실패");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(result);
        }
    }

    public boolean findByEmail (String email){
        UserPrincipal userPrincipal = userPrincipalRepository.findByEmail(email);
        if (userPrincipal != null){
            return true;
        }else {
            return false;
        }
    }

    public boolean findByPasswordCheck(String email,String password){
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String entityPassword = userPrincipalRepository.findByEmail(email).getPassword();

        return passwordEncoder.matches(password,entityPassword);
    }
}