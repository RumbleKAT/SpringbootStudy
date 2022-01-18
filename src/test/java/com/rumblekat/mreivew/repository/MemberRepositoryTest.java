package com.rumblekat.mreivew.repository;

import com.rumblekat.mreivew.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    public void insertMember(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder()
                    .email("r" +i+"@zerock.org")
                    .pw("1111")
                    .nickname("reviewer"+i).build();
            memberRepository.save(member);
        });
    }

    @Transactional
    @Commit
    @Test
    public void testDeleteMember(){
        Long mid = 3L;
        Member member = Member.builder().mid(mid).build();
        reviewRepository.deleteByMember(member); //FK부터 제거한다.
        memberRepository.deleteById(mid);

        /*
        * Hibernate: delete가 3번 호출되는 문제가 있음 -> @Modifying 어노테이션을 사용한다.
    delete
    from
        m_member
    where
        mid=?
        *
        * */
    }
}