package com.example.fsc.entity;


import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    private Long postId;
    private Long emailId;

    private String content;
    private String author;

    private Date createdAt;

    /**
     * 2023-09-19
     * 댓글 추가(빋더 생성)
     * 작성자: 김대한
     */
    @Builder
    public CommentEntity(Long commentId, Long postId, Long emailId, String content, String author, Date createdAt) {
        this.commentId = commentId;
        this.postId = postId;
        this.emailId = emailId;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }
}
