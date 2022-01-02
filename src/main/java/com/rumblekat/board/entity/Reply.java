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

    //TODO: Board와 연관관계 board의 writer까지 가져오면 모두 left outer join으로 가져오게된다.
    @ManyToOne
    private Board board;
}
