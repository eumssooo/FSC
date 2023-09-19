package com.example.fsc.controller.memberController;


import com.example.fsc.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
    public String signup(){
        System.out.println("member signup");
        return null;
    }

}
