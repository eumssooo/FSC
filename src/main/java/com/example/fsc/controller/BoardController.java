package com.example.fsc.controller;

import com.example.fsc.dto.BoardDTO;
import com.example.fsc.dto.UpdateBoardDto;
import com.example.fsc.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class BoardController {
    private final BoardService boardService;

//    @PostMapping("/posts")
//    public ResponseEntity<Map<String, String>> createArticle (@RequestBody BoardDTO boardDTO){
//        return boardService.saveBoard(boardDTO);
//    }
//
//
//    @PutMapping("/posts/{board_id}")
//    public ResponseEntity<Map<String, String>> modifyArticle (@PathVariable Long board_id,
//            @RequestBody UpdateBoardDto updateBoardDto){
//        return boardService.updateBoard(updateBoardDto, board_id);
//    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<BoardDTO>> searchArticleListByEmail (@RequestParam String author){
        return boardService.findBoardListByEmail(author);
    }

}
