package com.rumblekat.board.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer") //@ToString은 항상 exclude 연관관계가 있는 것은 무조건 exclude작업을 진행한다.
public class Board extends BaseEntity{
    /*
    * 게시물의 수정은 필요한 부분만을 변경하고 BoardRepository의 save를 이용하여 처리
    * */

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gno; //bno

    private String title;

    private String content;

    //작성자는 아직 처리하지 않는다.
    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer; //연관관계
    /*
    * JPA는 FK쪽을 먼저 해석한다. board와 member읜 관계는 N:1(다대일)
    * */

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeContent(String content){
        this.content = content;
    }

}
