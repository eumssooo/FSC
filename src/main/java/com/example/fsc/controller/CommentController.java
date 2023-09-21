package com.example.fsc.controller;



import com.example.fsc.dto.commentDto.CommentViewDto;
import com.example.fsc.security.JwtTokenProvider;
import com.example.fsc.service.CommentService;
import com.example.fsc.dto.commentDto.CreateCommentDto;
import com.example.fsc.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    private final MemberService memberService;


    @GetMapping("/comments")
    public ResponseEntity<List<CommentViewDto>> commentView(@RequestBody Map<String,Long> board_id){
       return commentService.postComment(board_id.get("board_id"));
    }



    @DeleteMapping("/comments/delete")
    public ResponseEntity<Map<String,String>> commentDelete(@RequestBody Map<String,Long> comment_id, HttpServletRequest request){
        String header = request.getHeader("loginUser");
        Map<String, String> token = memberService.showToken(header);
        return commentService.commentDelete(comment_id.get("comment_id"),token);
    }

    /**
     * 2023-09-19
     * 댓글 추가
     * 작성자: 김대한
     */
    // ResponseEntity 와 지네릭에 Map 을 사용하여 Key , Value값을 담았고
    // @RequestBody 어노테이션을 사용 함으로써
    // HTTP 요청 본문에 담긴 값들을 자바 객체로 전환 시켜서 객체에 저장 시켰습니다.
    @PostMapping("/comments")
    public ResponseEntity<Map<String, String>> createComment(@RequestBody CreateCommentDto createCommentDto , HttpServletRequest request) {
        String header = request.getHeader("loginUser");
        Map<String, String> token = memberService.showToken(header);
        return commentService.commentSave(createCommentDto , token);
    }

    // ResponseEntity 와 지네릭에 Map 을 사용하여 Key , Value값을 담았고
    // @PathVariable 을 사용 함으로써 URL 경로에서 변수 값을 추출 하여 매개변수에 할당했으며
    // @RequestBody 어노테이션을 사용 함으로써
    // HTTP 요청 본문에 담긴 값들을 자바 객체로 전환 시켜서 객체에 저장 시켰습니다.
    @PutMapping("/comments/{comment_id}")
    public ResponseEntity<Map<String,String>> updateComment(@PathVariable Long comment_id ,
                                                            @RequestBody Map<String,String> content ,
                                                            HttpServletRequest request){
        String header = request.getHeader("loginUser");
        Map<String, String> token = memberService.showToken(header);
        return commentService.commentUpdate(content,comment_id,token);
    }
}
