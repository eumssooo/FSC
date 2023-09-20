package com.example.fsc.service;

import com.example.fsc.domain.Board;
import com.example.fsc.dto.BoardDTO;
import com.example.fsc.dto.UpdateBoardDto;
import com.example.fsc.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

//    public ResponseEntity<Map<String, String>> saveBoard(BoardDTO boardDTO) {
//
//        // dto -> entity 변환
//        Board board = Board.builder().
//                author(boardDTO.getAuthor())
//                .title(boardDTO.getTitle())
//                .content(boardDTO.getContent())
//                .build();
//
//        Map<String, String> map = new HashMap<>();
//        Long boardId = boardRepository.save(board).getBoardId();
//        Optional<Board> findId = boardRepository.findById(boardId);
//        if(findId.isPresent()){
//            map.put("message", "게시물이 성공적으로 작성되었습니다.");
//        } else {
//            map.put("message", "게시물 작성에 실패하였습니다.");
//        }
//        return ResponseEntity.status(200).body(map);
//    }
//
//    public ResponseEntity<Map<String, String>> updateBoard(UpdateBoardDto updateBoardDto, Long boardId) {
//
//        Map<String, String> map = new HashMap<>();
//
//        Optional<Board> boardOptional = boardRepository.findByBoardId(boardId);
//
//        if(boardOptional.isPresent()){
//            Board board = boardOptional.get();
//            // dto -> entity 변환
//            board.setTitle(updateBoardDto.getTitle());
//            board.setContent(updateBoardDto.getContent());
//
//            boardRepository.save(board);
//            map.put("message", "게시물이 성공적으로 수정되었습니다.");
//        } else {
//            map.put("message", "게시물 수정에 실패하였습니다.");
//        }
//        return ResponseEntity.status(200).body(map);
//    }

    public ResponseEntity<List<BoardDTO>> findBoardListByEmail(String email) {
        List<Board> searchedBoardEntityList = boardRepository.findBoardsByAuthorContainingOrderByCreatedAtDesc(email);
        List<BoardDTO> searchedBoardDtoList = new ArrayList<>();
        // entity -> dto 변환 필요
        for(Board board : searchedBoardEntityList) {
            BoardDTO boardDTO = BoardDTO.builder()
                    .boardId(board.getBoardId())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .author(board.getAuthor())
                    .createdAt(board.getCreatedAt())
                    .build();
            searchedBoardDtoList.add(boardDTO);
        }
        return ResponseEntity.status(200).body(searchedBoardDtoList);
    }
}



