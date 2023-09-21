package com.example.fsc.entity;

import com.example.fsc.entity.BoardEntity;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity (name = "comment")
@Getter
@Setter
@NoArgsConstructor
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;
    @Column(name = "email_id")
    private Long emailId;
    @Column(name = "content")
    private String content;
    @Column(name = "author")
    private String author;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    /**
     * 2023-09-19
     * 댓글 추가(빋더 생성)
     * 작성자: 김대한
     */
    @Builder
    public CommentEntity(Long commentId, Long emailId, String content, String author, LocalDateTime createdAt , BoardEntity boardEntity) {
        this.commentId = commentId;
        this.emailId = emailId;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.boardEntity = boardEntity;
    }
}