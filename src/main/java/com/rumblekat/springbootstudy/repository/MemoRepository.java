package com.rumblekat.springbootstudy.repository;

import com.rumblekat.springbootstudy.guestbook.entity.Memo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MemoRepository extends JpaRepository<Memo,Long> {
    List<Memo> findByMnoBetweenOrderByMnoDesc(Long from, Long to);
    Page<Memo> findByMnoBetween(Long from, Long to, Pageable pageable);
    void deleteMemoByMnoLessThan(Long Mno);

    //Mno를 기준으로 역순으로 정렬하라
    @Query("select m from Memo m order by m.mno desc")
    List<Memo> getListDesc();

    //@Query의 파라미터 바인딩, where 구문과 그에 맞는 파라미터들을 처리

    @Transactional
    @Modifying
    @Query("update Memo m set m.memoText = :#{#param.memoText} where m.mno = :#{#param.mno}")
    int updateMemoText(@Param("param") Memo memo);

    @Query(value = "select m from Memo m where m.mno > :mno",
            countQuery = "select count(m) from Memo m where m.mno > :mno" )
    Page<Memo> getListWithQuery(Long mno, Pageable pageable);

    //Object[] 리턴 -> group by 시 유용한 객체로 리턴하기 위함
    @Query(value="select m.mno, m.memoText, CURRENT_DATE from Memo m where m.mno > :mno",
            countQuery = "select count(m) from Memo m where m.mno > :mno")
    Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);

    @Query(value="select * from memo where mno > 0",nativeQuery = true)
    List<Object[]> getNativeResult();
}
