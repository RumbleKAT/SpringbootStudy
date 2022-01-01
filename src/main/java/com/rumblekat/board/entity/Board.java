package com.rumblekat.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") //@ToString은 항상 exclude
public class Board extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno;

    private String title;

    private String content;

    //작성자는 아직 처리하지 않는다.
    @ManyToOne
    private Member writer; //연관관계
    /*
    * JPA는 FK쪽을 먼저 해석한다. board와 member읜 관계는 N:1(다대일)
    *
    * */
}
