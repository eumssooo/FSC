package com.example.fsc.controller;

import com.example.fsc.dto.BoardDto;
import com.example.fsc.dto.UpdateBoardDto;
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
    @GetMapping("/posts")
    public ResponseEntity<List<BoardDto>> viewBoardList() {
        return boardService.findAll();
    }

    //게시물 상세 조회
    @GetMapping("/posts/{boardId}")
    public ResponseEntity<BoardDto> searchBoardById(@PathVariable Long boardId) {
        return boardService.findBoardById(boardId);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<BoardDto>> searchBoardListByEmail(@RequestParam String author) {
        return boardService.findBoardListByEmail(author);
    }

    @DeleteMapping("/posts/delete")
    public ResponseEntity<Map<String, String>> deleteBoard(@RequestHeader("loginUser") String token, @RequestParam Long boardId) {
        return boardService.deleteBoard(token,boardId);
    }

    // 게시물 수정
    @PutMapping("/posts/{boardId}")
    public ResponseEntity<Map<String, String>> modifyBoard(@PathVariable Long boardId, @RequestHeader("loginUser") String token,
                                                           @RequestBody UpdateBoardDto updateBoardDto) {
        return boardService.updateBoard(updateBoardDto, token, boardId);
    }

    @PostMapping("/posts")
    public ResponseEntity<Map<String, String>> createBoard(@RequestHeader("loginUser") String token
            , @RequestBody BoardDto boardDTO) {
        return boardService.saveBoard(token,boardDTO);
    }


}
