package com.example.fsc.controller;



import com.example.fsc.dto.CommentDto;
import com.example.fsc.dto.CommentViewDto;
import com.example.fsc.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/comments")
    public ResponseEntity<List<CommentViewDto>> commentView(@RequestParam("post_id") Long post_id){
       return commentService.postComment(post_id);
    }

    @DeleteMapping("/comments/delete")
    public ResponseEntity<Map<String,String>> commentDelete(@RequestParam("comment_id") Long comment_id){
        return commentService.commentDelete(comment_id);
    }
}
