package com.example.fsc.service;

import com.example.fsc.domain.Board;
import com.example.fsc.dto.BoardDTO;
import com.example.fsc.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public ResponseEntity<BoardDTO> findById(Long id){
        Optional<Board> byId = boardRepository.findById(id);
            Board board = byId.get();
            BoardDTO boardDTO = BoardDTO.builder()
                    .boardId(board.getBoardId())
                    .emailId(board.getEmailId())
                    .title(board.getTitle())
                    .author(board.getAuthor())
                    .content(board.getContent())
                    .createdAt(board.getCreatedAt())
                    .build();
            return ResponseEntity.status(200).body(boardDTO);
        }

    public ResponseEntity<List<BoardDTO>> findBoardListByEmail(String email) {
        List<Board> searchedBoardEntityList = boardRepository.findBoardsByAuthorContainingOrderByCreatedAtDesc(email);
        List<BoardDTO> searchedBoardDtoList = new ArrayList<>();

        for(Board board : searchedBoardEntityList) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .boardId(board.getBoardId())
                    .emailId(board.getEmailId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .author(board.getAuthor())
                    .createdAt(board.getCreatedAt())
                    .build();
            searchedBoardDtoList.add(boardDTO);
        }
        return ResponseEntity.status(200).body(searchedBoardDtoList);
    }

        public ResponseEntity<Map<String ,String>> deleteBoard(Long boardId) {

        Map<String,String> deleteMap= new HashMap<>();
        boardRepository.deleteById(boardId);
        Optional<Board> byId = boardRepository.findById(boardId);
        if (!byId.isPresent()){
            deleteMap.put("message" ," 게시물이 성공적으로 삭제되었습니다.");
        }else{
            deleteMap.put("message", "게시물이 삭제 되지 않았습니다.");
        }
        return ResponseEntity.status(200).body(deleteMap);
        }



}



