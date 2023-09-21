package com.example.fsc.service;

import com.example.fsc.entity.boardEntity.BoardEntity;
import com.example.fsc.dto.boardDto.BoardDto;
import com.example.fsc.dto.boardDto.UpdateBoardDto;
import com.example.fsc.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static org.springframework.http.RequestEntity.put;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public ResponseEntity<List<BoardDto>> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDto> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            BoardDto boardDTO = BoardDto.builder()
                    .boardId(boardEntity.getBoardId())
                    .title(boardEntity.getTitle())
                    .content(boardEntity.getContent())
                    .author(boardEntity.getAuthor())
                    .emailId(boardEntity.getEmailId())
                    .createdAt(boardEntity.getCreatedAt())
                    .build();
            boardDTOList.add(boardDTO);
        }
        return ResponseEntity.status(200).body(boardDTOList);
    }

    public ResponseEntity<BoardDto> findBoardById(Long id){
        Optional<BoardEntity> byId = boardRepository.findById(id);
            BoardEntity boardEntity = byId.get();
            BoardDto boardDTO = BoardDto.builder()
                    .boardId(boardEntity.getBoardId())
                    .emailId(boardEntity.getEmailId())
                    .title(boardEntity.getTitle())
                    .author(boardEntity.getAuthor())
                    .content(boardEntity.getContent())
                    .createdAt(boardEntity.getCreatedAt())
                    .build();
            return ResponseEntity.status(200).body(boardDTO);
        }

    public ResponseEntity<List<BoardDto>> findBoardListByEmail(String email) {
        List<BoardEntity> searchedBoardEntityListEntity = boardRepository.findBoardsByAuthorContainingOrderByCreatedAtDesc(email);
        List<BoardDto> searchedBoardDtoList = new ArrayList<>();

        for(BoardEntity boardEntity : searchedBoardEntityListEntity) {
            BoardDto boardDTO = BoardDto.builder()
                    .boardId(boardEntity.getBoardId())
                    .emailId(boardEntity.getEmailId())
                    .title(boardEntity.getTitle())
                    .content(boardEntity.getContent())
                    .author(boardEntity.getAuthor())
                    .createdAt(boardEntity.getCreatedAt())
                    .build();
            searchedBoardDtoList.add(boardDTO);
        }
        return ResponseEntity.status(200).body(searchedBoardDtoList);
    }

        public ResponseEntity<Map<String ,String>> deleteBoard(Long boardId) {

        Map<String,String> deleteMap= new HashMap<>();
        boardRepository.deleteById(boardId);
        Optional<BoardEntity> byId = boardRepository.findById(boardId);
        if (!byId.isPresent()){
            deleteMap.put("message" ," 게시물이 성공적으로 삭제되었습니다.");
        }else{
            deleteMap.put("message", "게시물이 삭제 되지 않았습니다.");
        }
        return ResponseEntity.status(200).body(deleteMap);
        }


    public ResponseEntity<Map<String, String>> updateBoard(UpdateBoardDto updateBoardDto, Long boardId) {

        Map<String, String> map = new HashMap<>();

        Optional<BoardEntity> boardOptional = boardRepository.findById(boardId);

        if(boardOptional.isPresent()){
            BoardEntity boardEntity = boardOptional.get();
            // dto -> entity 변환
            boardEntity.setTitle(updateBoardDto.getTitle());
            boardEntity.setContent(updateBoardDto.getContent());

            boardRepository.save(boardEntity);
            map.put("message", "게시물이 성공적으로 수정되었습니다.");
        } else {
            map.put("message", "게시물 수정에 실패하였습니다.");
        }
        return ResponseEntity.status(200).body(map);
    }


    public ResponseEntity<Map<String, String>> saveBoard(BoardDto boardDTO) {

        // dto -> entity 변환
        BoardEntity boardEntity = BoardEntity.builder().
                author(boardDTO.getAuthor())
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .emailId(boardDTO.getEmailId())
                .createdAt(LocalDateTime.now())
                .build();

        Map<String, String> map = new HashMap<>();
        Long boardId = boardRepository.save(boardEntity).getBoardId();
        Optional<BoardEntity> findId = boardRepository.findById(boardId);
        if(findId.isPresent()){
            map.put("message", "게시물이 성공적으로 작성되었습니다.");
        } else {
            map.put("message", "게시물 작성에 실패하였습니다.");
        }
        return ResponseEntity.status(200).body(map);
    }




}



