package com.example.fsc.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class CreateCommentDto {
    /**
     * 2023-09-19
     * 댓글 추가
     * 작성자: 김대한
     */
    private Long boardId;

    private String content;
    private String author;



}
