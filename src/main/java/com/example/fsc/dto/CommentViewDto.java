package com.example.fsc.dto;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
public class CommentViewDto {
    private Long commentId;
    private Long boardId;
    private String content;
    private String author;
    private Date createAt;


    @Builder
    public CommentViewDto(Long commentId, Long boardId, String content, String author , Date createAt){
        this.commentId = commentId;
        this.boardId = boardId;
        this.content = content;
        this.author = author;
        this.createAt = createAt;
    }
}
