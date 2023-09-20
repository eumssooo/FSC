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

}
