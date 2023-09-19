package com.example.fsc.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDTO {

    private Long boardId;

    private Long emailId;

    private String author;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    @Builder
    public BoardDTO(Long boardId, String author, String title, String content, LocalDateTime createdAt) {
        this.boardId = boardId;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
