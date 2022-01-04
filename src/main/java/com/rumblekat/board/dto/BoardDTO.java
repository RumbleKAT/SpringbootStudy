package com.rumblekat.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long bno;
    private String title;
    private String content;
    private String writerEmail; //작성자의 이메일
    private String writerName; //작성자 명
    private LocalDateTime regDate;
    private LocalDateTime modDate;
    private int replyCount; //해당 게시글 댓글의 수
}
