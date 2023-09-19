package com.example.fsc.controller.memberController;


import com.example.fsc.entity.memberEntity.MemberEntity;
import com.example.fsc.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    //이메일로 회원가입하기
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> signup(@RequestBody MemberEntity memberEntity){
        if(memberService.isEmailUnique((memberEntity.getEmail()))){
            memberService.signup(memberEntity);
            return ResponseEntity.ok("회원가입이 완료되었습니다.");
        }else {
            return ResponseEntity.badRequest().body("이미 가입된 이메일입니다.");
        }

    }

}
