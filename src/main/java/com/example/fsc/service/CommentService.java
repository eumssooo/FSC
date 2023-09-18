package com.example.fsc.service;

import com.example.fsc.dto.CreateCommentDto;
import com.example.fsc.entity.CommentEntity;
import com.example.fsc.repository.CommentRepository;

import com.example.fsc.dto.CommentDto;
import com.example.fsc.dto.CommentViewDto;
import com.example.fsc.entity.CommentEntity;
import com.example.fsc.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public ResponseEntity<List<CommentViewDto>> postComment(Long post_id) {
        List<CommentEntity> commentEntityList = commentRepository.findByPostIdOrderByCreatedAtAsc(post_id);
        List<CommentViewDto> commentDtoList = new ArrayList<>();
        for (CommentEntity comment: commentEntityList) {
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
    public ResponseEntity<Map<String,String>> commentDelete(Long commentId) {
        Map<String,String> result = new HashMap<>();
        Optional<CommentEntity> deleteComment = commentRepository.findById(commentId);
        if(deleteComment.isPresent()){
            commentRepository.deleteById(commentId);
            result.put("message","댓글이 정상적으로 삭제되었습니다.");
            return ResponseEntity.status(200).body(result);
        }else {
            result.put("message","댓글이 삭제안됨");
            return ResponseEntity.status(200).body(result);
        }
    }





    /**
     * 2023-09-19
     * 댓글 추가
     * 작성자: 김대한
     */
    public ResponseEntity<Map<String, String>> commentSave(CreateCommentDto createCommentDto) {
        CommentEntity commentEntity = CommentEntity.builder()
                .author(createCommentDto.getAuthor())
                .postId(createCommentDto.getPostId())
                .content(createCommentDto.getContent())
                .build();

        Map<String, String> map = new HashMap<>();

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
}