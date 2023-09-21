package com.example.fsc.controller.memberController;


import com.example.fsc.entity.memberEntity.MemberEntity;
import com.example.fsc.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    //회원가입
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> signup(@RequestBody MemberEntity memberEntity){

        if(memberService.isEmailUnique((memberEntity.getEmail()))){
            return memberService.signup(memberEntity);
        }else {
            Map<String, String> result = new HashMap<>();
            result.put("message", "회원가입에 실패했습니다.");
            return ResponseEntity.status(200).body(result);
        }
    }

    //로그인하기
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<Map<String, String >> login(@RequestBody MemberEntity memberEntity, HttpServletResponse httpServletResponse){
        //입력정보 서비스에 넘겨주기
        return memberService.login(memberEntity,httpServletResponse);
    }


    //로그아웃
    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
   public ResponseEntity<Map<String, String >> logout(@RequestBody MemberEntity memberEntity, HttpServletResponse httpServletResponse){
        return memberService.logout(memberEntity,httpServletResponse);
    }
}
