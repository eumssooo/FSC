package com.example.fsc.ex.controller;

import com.example.fsc.ex.dto.auth.Login;
import com.example.fsc.ex.dto.auth.SignUp;
import com.example.fsc.ex.service.security.AuthService;
import com.example.fsc.ex.userDetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class SignController {

    private final AuthService authService;

    @PostMapping(value = "/signup")
    public ResponseEntity<Map<String,String >> register(@RequestBody SignUp signUpRequest){
        boolean isSuccess = authService.signUp(signUpRequest);
        Map<String ,String> map = new HashMap<>();
        if(isSuccess) map.put("message", "회원가입에 성공했습니다.");
        else map.put("message", "회원가입에 실패했습니다.");
        return ResponseEntity.status(200).body(map);

    }

    @PostMapping(value = "/login")
    public ResponseEntity<Map<String,String >> login(@RequestBody Login loginRequest, HttpServletResponse httpServletResponse){
        return authService.login(loginRequest,httpServletResponse);
    }

    //로그아웃
    @PostMapping( "/logout")
    public ResponseEntity<Map<String,String >> logout(@AuthenticationPrincipal CustomUserDetails customUserDetails,HttpServletResponse httpServletResponse){
        Map<String ,String> map = new HashMap<>();
        httpServletResponse.setHeader(null,null);
        Long emailId=customUserDetails.getUserId();
        String email = customUserDetails.getEmail();
        System.out.println(email +" : "+emailId);
        map.put("message", customUserDetails.getEmail()+" 계정이 로그아웃 되었습니다.");
        return ResponseEntity.status(200).body(map);
    }
}
