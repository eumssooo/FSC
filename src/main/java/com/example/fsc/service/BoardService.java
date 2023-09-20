package com.example.fsc.service;

import com.example.fsc.domain.Board;
import com.example.fsc.dto.BoardDTO;
import com.example.fsc.dto.UpdateBoardDto;
import com.example.fsc.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardService {
    private final BoardRepository boardRepository;

    public ResponseEntity<Map<String, String>> saveBoard(BoardDTO boardDTO) {

        // dto -> entity 변환 필요


        Map<String, String> map = new HashMap<>();
        Long boardId = boardRepository.save(board).getBoardId();
//        if(boardId != 0)
        map.put("message", "게시물이 성공적으로 작성되었습니다.");
        return ResponseEntity.status(200).body(map);
    }

    public ResponseEntity<Map<String, String>> updateBoard(UpdateBoardDto updateBoardDto, Long boardId) {


        Map<String, String> map = new HashMap<>();
        // dto -> entity 변환 필요

        Optional<Board> boardOptional = boardRepository.findByBoardId(boardId);

        if(boardOptional.isPresent()){
            Board board = boardOptional.get();
            board.setTitle(updateBoardDto.getTitle());
            board.setContent(updateBoardDto.getContent());
            boardRepository.save(board);
            map.put("message", "게시물이 성공적으로 수정되었습니다.");
        } else {
            map.put("message", "게시물 수정에 실패하였습니다.");
        }
        return ResponseEntity.status(200).body(map);
    }

    public ResponseEntity<List<BoardDTO>> findBoardListByEmail(String email) {
        List<Board> searchedBoardEntityList = boardRepository.findBoardsByAuthorContainingOrderByCreatedAtDesc(email);
        List<BoardDTO> searchedBoardDtoList = new ArrayList<>();
        // entity -> dto 변환 필요

        return ResponseEntity.status(200).body();
    }
}



