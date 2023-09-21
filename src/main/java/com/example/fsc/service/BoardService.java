package com.example.fsc.service;

import com.example.fsc.entity.BoardEntity;
import com.example.fsc.dto.BoardDto;
import com.example.fsc.dto.UpdateBoardDto;
import com.example.fsc.repository.BoardRepository;
import com.example.fsc.repository.MemberRepository;
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
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    public ResponseEntity<List<BoardDto>> findAll() {
        // entity -> dto
        List<BoardEntity> boardList = boardRepository.findAll();
        List<BoardDto> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardList) {
            BoardDto boardDTO = BoardDto
                    .builder()
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

    public ResponseEntity<BoardDto> findBoardById(Long id) {
        // boardId에 해당하는　boardEntity 가져오기
        Optional<BoardEntity> byId = boardRepository.findById(id);
        BoardEntity boardEntity = byId.get();
        // entity -> dto
        BoardDto boardDTO = BoardDto
                .builder()
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
        // 검색어 email을 포함하는 boardEntity를 리스트로 가져오기
        List<BoardEntity> searchedBoardEntityList = boardRepository.findBoardsByAuthorContainingOrderByCreatedAtDesc(email);
        List<BoardDto> searchedBoardDtoList = new ArrayList<>();
        // entity -> dto
        for (BoardEntity boardEntity : searchedBoardEntityList) {
            BoardDto boardDTO = BoardDto
                    .builder()
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

    public ResponseEntity<Map<String, String>> deleteBoard(String token, Long boardId) {

        Map<String, String> deleteMap = new HashMap<>();
        // 삭제할 boardId에 해당하는 BoardEntity 가져오기
        Optional<BoardEntity> boardOptional = boardRepository.findById(boardId);

        if (boardOptional.isPresent()) { // 게시물이 존재할 때
            // 토큰 값 꺼내기
            String userEmail = (String) memberService.showToken(token).get("email");
            String author = (String) boardOptional.get().getAuthor();

            // 로그인한 아이디와 작성자 아이디 비교
            if (author.equals(userEmail)) { // 로그인한 아이디와 작성자 아이디가 같을 때
                boardRepository.deleteById(boardId); // 삭제 진행

                //  삭제 진행 확인
                Optional<BoardEntity> deletedBoardOptional = boardRepository.findById(boardId);

                if (deletedBoardOptional.isEmpty()) {
                    deleteMap.put("message", " 게시물이 성공적으로 삭제되었습니다.");
                } else {
                    deleteMap.put("message", "게시물이 삭제되지 않았습니다.");
                }

            } else { // 아이디가 다를 때
                deleteMap.put("message", "작성자가 아닙니다. 삭제할 수 없습니다.");
            }

        } else { // 게시물이 존재하지 않을 때
            deleteMap.put("message", "게시물이 존재하지 않습니다.");
        }


        return ResponseEntity.status(200).body(deleteMap);
    }


    public ResponseEntity<Map<String, String>> updateBoard(UpdateBoardDto updateBoardDto, String token, Long boardId) {

        Map<String, String> map = new HashMap<>();
        // 수정할 boardId에 해당하는 BoardEntity 가져오기
        Optional<BoardEntity> boardOptional = boardRepository.findById(boardId);

        if (boardOptional.isPresent()) { // 게시물이 존재할 때
            // 토큰 값 꺼내기
            String userEmail = (String) memberService.showToken(token).get("email");
            // BoardEntity 값 꺼내기
            BoardEntity boardEntity = boardOptional.get();
            // 작성자 꺼내기
            String author = boardEntity.getAuthor();

            if (author.equals(userEmail)) { // 로그인한 아이디와 작성자 아이디가 같을 때
                // dto -> entity 변환
                boardEntity.setTitle(updateBoardDto.getTitle());
                boardEntity.setContent(updateBoardDto.getContent());

                boardRepository.save(boardEntity); // 저장
                map.put("message", "게시물이 성공적으로 수정되었습니다.");
            } else {
                map.put("message", "작성자가 아닙니다. 수정할 수 없습니다.");
            }

        } else {
            map.put("message", "게시물이 존재하지 않습니다.");
        }
        return ResponseEntity.status(200).body(map);
    }


    public ResponseEntity<Map<String, String>> saveBoard(String token, BoardDto boardDTO) {

        // 토큰 값 꺼내기
        String userEmail = (String) memberService.showToken(token).get("email");

        // dto -> entity 변환
        BoardEntity boardEntity = BoardEntity.builder()
                .author(userEmail)
                .title(boardDTO.getTitle())
                .content(boardDTO.getContent())
                .emailId(memberRepository.findByEmail(userEmail).getEmailId())
                .createdAt(LocalDateTime.now())
                .build();

        Map<String, String> map = new HashMap<>();

        // 저장 후  boardId 가져오기
        Long boardId = boardRepository.save(boardEntity).getBoardId();

        // 게시물 작성 완료 확인
        Optional<BoardEntity> findId = boardRepository.findById(boardId);
        if (findId.isPresent()) {
            map.put("message", "게시물이 성공적으로 작성되었습니다.");
        } else {
            map.put("message", "게시물 작성에 실패하였습니다.");
        }

        return ResponseEntity.status(200).body(map);
    }


}



