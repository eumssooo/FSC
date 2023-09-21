package com.example.fsc.service;

import com.example.fsc.dto.commentDto.CommentViewDto;
import com.example.fsc.dto.commentDto.CreateCommentDto;
import com.example.fsc.entity.boardEntity.BoardEntity;
import com.example.fsc.entity.commentEntity.CommentEntity;
import com.example.fsc.repository.BoardRepository;
import com.example.fsc.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public ResponseEntity<List<CommentViewDto>> postComment(Long board_id) {
        List<CommentEntity> commentEntityList = commentRepository.findByBoardEntity_BoardId(board_id);
        List<CommentViewDto> commentDtoList = new ArrayList<>();
        for (CommentEntity comment : commentEntityList) {
            CommentViewDto dto = CommentViewDto.builder()
                    .commentId(comment.getCommentId())
                    .boardId(comment.getBoardEntity().getBoardId())
                    .content(comment.getContent())
                    .author(comment.getAuthor())
                    .createAt(comment.getCreatedAt())
                    .build();
            commentDtoList.add(dto);
        }
        return ResponseEntity.status(200).body(commentDtoList);
    }

    public ResponseEntity<Map<String, String>> commentDelete(Long commentId, Map<String, String> token) {
        Map<String, String> result = new HashMap<>();
        Optional<CommentEntity> deleteComment = commentRepository.findById(commentId);
        String email = token.get("email");

        if (deleteComment.isPresent()) {
            if (email.equals(deleteComment.get().getAuthor())) {
                commentRepository.deleteById(commentId);
                result.put("message", "댓글이 정상적으로 삭제되었습니다.");
                return ResponseEntity.status(200).body(result);
            } else {
                result.put("message", "작성자가 다릅니다..");
                return ResponseEntity.status(200).body(result);
            }
        } else {
            result.put("message", "댓글이 삭제안됨");
            return ResponseEntity.status(200).body(result);
        }
    }

    /**
     * 2023-09-19
     * 댓글 추가
     * 작성자: 김대한
     */
    public ResponseEntity<Map<String, String>> commentSave(CreateCommentDto createCommentDto, Map<String, String> token) {
        BoardEntity boardEntity = boardRepository.findById(createCommentDto.getBoard_id()).get();
        // dto -> Entity로 전환
        CommentEntity commentEntity = CommentEntity.builder()
                .author(token.get("email"))
                .content(createCommentDto.getContent())
                .boardEntity(boardEntity)
                .emailId(Long.valueOf(token.get("emailId")))
                .createdAt(LocalDateTime.now())
                .build();

        Map<String, String> map = new HashMap<>();
        // db에 Comment 저장하는 result 변수 생성
        Long result = commentRepository.save(commentEntity).getCommentId();
        // 레파지토리에 아이디를 찾아서 그 아이디에 Comment를 CommentEntity에 저장 한다.
        Optional<CommentEntity> findId = commentRepository.findById(result);
        // 만약 아이디가 존재 할 경우 "message", "댓글이 성공적으로 작성되었습니다." 실행과 200 실행
        if (findId.isPresent()) {
            map.put("message", "댓글이 성공적으로 작성되었습니다.");
            return ResponseEntity.status(200).body(map);
            //그러지 않을 경우 댓글 작성에 실패 하였습니다. 실행
        } else {
            map.put("message", "댓글 작성에 실패 하였습니다.");
            return ResponseEntity.status(200).body(map);
        }
    }

    /**
     * 2023-09-19
     * 댓글 수정
     * 작성자: 김대한
     */
    public ResponseEntity<Map<String, String>> commentUpdate(Map<String, String> content,
                                                             Long comment_id, Map<String, String> token) {


        Map<String, String> map = new HashMap<>();
        //1 : comentId로 검색을한다
        Optional<CommentEntity> updateId = commentRepository.findById(comment_id);
        //2 : 검색을해서 있으면 if문으로 들어온다
        if (updateId.isPresent()) {
            //3.검색된 댓글을 가져온다
            CommentEntity commentEntity = updateId.get();
            // 이게 먼줄아십니까?
            if(commentEntity.getAuthor().equals(token.get("email"))){
                //4.가져온거에서 수정된거만 수정한다
                commentEntity.setContent(content.get("content"));
                //5.다시 db에 넣는다
                commentRepository.save(commentEntity);
                //6.넣고나서 리턴을해준다
                map.put("meassage", "댓글이 성공적으로 수정되었습니다.");
                return ResponseEntity.status(200).body(map);
            }else{
                map.put("meassage", "댓글 작성자가 틀립니다..");
                return ResponseEntity.status(200).body(map);
            }
        } else {
            map.put("massage", "댓글 수정에 실패하였습니다.");
            return ResponseEntity.status(200).body(map);


        }
    }
}