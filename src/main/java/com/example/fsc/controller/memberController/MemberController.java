package com.example.fsc.controller.memberController;


import com.example.fsc.entity.memberEntity.MemberEntity;
import com.example.fsc.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    //이메일로 회원가입하기
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> signup(@RequestBody MemberEntity memberEntity){
        System.out.println(memberEntity.getEmail());
        System.out.println(memberEntity.getPassword());
        Map<String,String> result = new HashMap<>();
        if(memberService.isEmailUnique((memberEntity.getEmail()))){

            memberService.signup(memberEntity);
            result.put("message","회원가입이 완료되었습니다.");
            return ResponseEntity.status(200).body(result);
        }else {
            result.put("message","회원가입에 실패했습니다.");
            return ResponseEntity.status(200).body(result);
        }

    }

}
