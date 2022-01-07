package com.rumblekat.board.repository;

import com.rumblekat.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    //JPQL을 이용해서 update, delete를 수행하기 위해선 Modifying이 필요하다.

    @Modifying
    @Query("delete from Reply r where r.board.gno = :bno")
    void deleteByBno(Long bno);
}
