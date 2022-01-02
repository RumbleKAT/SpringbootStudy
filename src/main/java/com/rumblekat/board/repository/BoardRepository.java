package com.rumblekat.board.repository;

import com.rumblekat.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<Board, Long> {
    //한개의 row 오브젝트 내에 Object []로 나온다.
    //TODO: JPQL을 이용한 쿼리
    @Query("select b, w from Board b left join b.writer w where b.gno = :bno ")
    Object getBoardWithWriter(@Param("bno") Long bno);

    //Lazy 로딩 처리 되었으나, 실제 쿼리를 수행할 땐, 조인 처리가 되어 한번에 board 테이블과 member 테이블을 이용한다.
    //연관관계가 없을 경우, on을 사용한다. 

    /*
    * getBoardWithWriter()는 Board를 사용하고 있지만, Member를 같이 조회해야하는 상황
    * 내부에 있는 엔티티를 이용할 땐, Left join 뒤에 on을 이용하는 부분이 없다.
    * */
}
