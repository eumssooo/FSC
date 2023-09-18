package com.example.fsc.service;


import com.example.fsc.dto.CommentDto;
import com.example.fsc.dto.CommentViewDto;
import com.example.fsc.entity.CommentEntity;
import com.example.fsc.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

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


}
