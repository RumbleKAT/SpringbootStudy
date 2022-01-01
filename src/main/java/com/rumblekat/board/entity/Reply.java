package com.rumblekat.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board") //@ToString에 주의(연관관계 대상 필드 exclude)
public class Reply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;

    private String replyer;

    //TODO: Board와 연관관계
    @ManyToOne
    private Board board;
}
