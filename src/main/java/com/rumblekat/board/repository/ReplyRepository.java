package com.rumblekat.board.repository;

import com.rumblekat.board.entity.Board;
import com.rumblekat.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    //JPQL을 이용해서 update, delete를 수행하기 위해선 Modifying이 필요하다.
    //Board 삭제시에 댓글들 삭제
    @Modifying
    @Query("delete from Reply r where r.board.gno = :bno")
    void deleteByBno(Long bno);

    //게시물로 댓글 목록 가져오기
    List<Reply> getRepliesByBoardOrderByRno(Board board);

}
