package com.example.fsc.controller;



import com.example.fsc.dto.CommentDto;
import com.example.fsc.dto.CommentViewDto;
import com.example.fsc.service.CommentService;
import com.example.fsc.dto.CreateCommentDto;
import com.example.fsc.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/comments")
    public ResponseEntity<List<CommentViewDto>> commentView(@RequestBody Map<String,Long> post_id){
        System.out.println(post_id.get("post_id"));
       return commentService.postComment(post_id.get("post_id"));
    }

    @DeleteMapping("/comments/delete")
    public ResponseEntity<Map<String,String>> commentDelete(@RequestBody Map<String,Long> comment_id){
        return commentService.commentDelete(comment_id.get("comment_id"));
    }

    /**
     * 2023-09-19
     * 댓글 추가
     * 작성자: 김대한
     */
    @PostMapping("/comments")
    public ResponseEntity<Map<String, String>> createComment(@RequestBody CreateCommentDto createCommentDto) {
        return commentService.commentSave(createCommentDto);
    }
}
