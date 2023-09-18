package com.example.fsc.service;

import com.example.fsc.dto.CommentViewDto;
import com.example.fsc.dto.CreateCommentDto;
import com.example.fsc.dto.UpdateCommentDto;
import com.example.fsc.entity.CommentEntity;
import com.example.fsc.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public ResponseEntity<List<CommentViewDto>> postComment(Long post_id) {
        List<CommentEntity> commentEntityList = commentRepository.findByPostIdOrderByCreatedAtAsc(post_id);
        List<CommentViewDto> commentDtoList = new ArrayList<>();
        for (CommentEntity comment : commentEntityList) {
            CommentViewDto dto = CommentViewDto.builder()
                    .commentId(comment.getCommentId())
                    .content(comment.getContent())
                    .author(comment.getAuthor())
                    .postId(comment.getPostId())
                    .createAt(comment.getCreatedAt())
                    .build();
            commentDtoList.add(dto);
        }
        return ResponseEntity.status(200).body(commentDtoList);
    }

    public ResponseEntity<Map<String, String>> commentDelete(Long commentId) {
        Map<String, String> result = new HashMap<>();
        Optional<CommentEntity> deleteComment = commentRepository.findById(commentId);
        if (deleteComment.isPresent()) {
            commentRepository.deleteById(commentId);
            result.put("message", "댓글이 정상적으로 삭제되었습니다.");
            return ResponseEntity.status(200).body(result);
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
    public ResponseEntity<Map<String, String>> commentSave(CreateCommentDto createCommentDto) {
        // dto -> Entity로 전환
        CommentEntity commentEntity = CommentEntity.builder()
                .author(createCommentDto.getAuthor())
                .postId(createCommentDto.getPostId())
                .content(createCommentDto.getContent())
                .build();

        Map<String, String> map = new HashMap<>();
        // db에 Comment 저장
        Long result = commentRepository.save(commentEntity).getCommentId();
        Optional<CommentEntity> findId = commentRepository.findById(result);
        if (findId.isPresent()) {
            map.put("message", "댓글이 성공적으로 작성되었습니다.");
            return ResponseEntity.status(200).body(map);
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
    public ResponseEntity<Map<String, String>> commentUpdate(UpdateCommentDto updateCommentDto, Long commentId) {


        Map<String, String> map = new HashMap<>();
        //1 : comentId로 검색을한다
        Optional<CommentEntity> updateId = commentRepository.findById(commentId);
        //2 : 검색을해서 있으면 if문으로 들어온다
        if (updateId.isPresent()) {
            //3.검색된 댓글을 가져온다
            CommentEntity commentEntity = updateId.get();
            //가져온거에서 수정된거만 수정한다
            commentEntity.setContent(updateCommentDto.getContent());
            //다시 db에 넣는다
            commentRepository.save(commentEntity);
            //넣고나서 리턴을해준다
            map.put("meassage", "댓글이 성공적으로 수정되었습니다.");
            return ResponseEntity.status(200).body(map);

        } else {
            map.put("massage", "댓글 수정에 실패하였습니다.");
            return ResponseEntity.status(200).body(map);


        }
    }
}