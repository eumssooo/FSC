package com.example.fsc.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BoardDto {

    private Long boardId;

    private Long emailId;

    private String author;

    private String title;

    private String content;

    private LocalDateTime createdAt;

    @Builder
    public BoardDto(Long boardId, Long emailId, String author, String title, String content, LocalDateTime createdAt) {
        this.boardId = boardId;
        this.emailId = emailId;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }
}
