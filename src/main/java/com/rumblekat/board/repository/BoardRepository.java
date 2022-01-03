package com.rumblekat.board.repository;

import com.rumblekat.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

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

    /*
    * 특정 게시물과 해당 게시물에 속한 댓글들을 조회해야하는 상황을 생각하면 board와 reply 테이블을 조인하여 쿼리를 작성한다.
    * select board.gno, board.title, board.writer_email, rno, text
    * from board left outer join reply
    *   on reply.board_gno = board.gno
    * where board.gno = 100;
    *
    * */
    @Query("SELECT b, r FROM Board b left join Reply r on r.board = b where b.gno = :bno")
    List<Object [] > getBoardWithReply(@Param("bno") Long bno);

    @Query(value= "select  b, w, count(r) " +
        " From Board b " +
            " Left join b.writer w "+
            " LEFT  join Reply r on r.board = b " +
            " group by  b ",
            countQuery = "select count(b) from Board b")
    Page<Object[]> getBoardWithReplyCount(Pageable pageable); //목록 화면에 필요한 데이터

    //조회 화면에 필요한 정보
    @Query(" select b, w, count(r) from Board b left join b.writer w " +
            " left outer join Reply r on r.board = b" +
            " where b.gno = :bno")
    Object getBoardByBno(@Param("bno") Long bno);

}

