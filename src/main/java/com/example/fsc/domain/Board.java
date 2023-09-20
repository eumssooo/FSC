package com.example.fsc.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Table(name = "board")
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long boardId;

    private Long emailId;

    private String author;

    private String title;

    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

}
