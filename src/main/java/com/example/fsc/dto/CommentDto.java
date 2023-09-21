package com.example.fsc.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {

    private Long commentId;

    private Long boardId;
    private Long emailId;

    private String content;
    private String author;

    private LocalDateTime createAt;

}
