package com.example.fsc.controller;

import com.example.fsc.dto.BoardDTO;
import com.example.fsc.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //게시물 전체 조회
    @GetMapping( "/posts")
    public ResponseEntity<List<BoardDTO>> findAll(){
        return boardService.findAll();
    }

    //게시물 상세 조회
    @GetMapping("/posts/{board_id}")
    public ResponseEntity<BoardDTO> findById(@PathVariable Long board_id){
        return boardService.findById(board_id);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<BoardDTO>> searchArticleListByEmail (@RequestParam String author){
        return boardService.findBoardListByEmail(author);
    }



}
