package com.example.fsc.service;

import com.example.fsc.domain.Board;
import com.example.fsc.dto.BoardDTO;
import com.example.fsc.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.RequestEntity.put;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public ResponseEntity<List<BoardDTO>> findAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (Board board : boardList) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .author(board.getAuthor())
                    .emailId(board.getEmailId())
                    .createdAt(board.getCreatedAt())
                    .build();
            boardDTOList.add(boardDTO);
        }
        return ResponseEntity.status(200).body(boardDTOList);
    }
}



