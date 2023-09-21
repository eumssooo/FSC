package com.example.fsc.dto.commentDto;


import lombok.*;

import java.time.LocalDateTime;

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
