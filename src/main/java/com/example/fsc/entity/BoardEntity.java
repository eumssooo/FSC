package com.example.fsc.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "board")
@NoArgsConstructor
public class BoardEntity {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long boardId;

    @Column(name = "email_id")
    private Long emailId;

    private String author;

    private String title;

    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public BoardEntity(Long boardId, Long emailId, String author, String title, String content, LocalDateTime createdAt) {
        this.boardId = boardId;
        this.emailId = emailId;
        this.author = author;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

}
