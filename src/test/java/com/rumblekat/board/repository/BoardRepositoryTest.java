package com.rumblekat.board.repository;

import com.rumblekat.board.entity.Board;
import com.rumblekat.board.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @Test
    public void insertBoard(){
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder().email("user" + i +"@aaa.com").build();
            Board board = Board.builder()
                    .title("Title..." + i)
                    .content("Content..." + i)
                    .writer(member)
                    .build();

            boardRepository.save(board);
        });
    }

    @Transactional //Lazy Loading을 처리하기 위한 transactional 태그추가
    @Test
    public void testRead1(){
        Optional<Board> result = boardRepository.findById(100L);

        Board board = result.get();
        System.out.println(board);
        System.out.println(board.getWriter()); //-> select를 두번 날린다.
        //ManyToOne으로 참조하고 있는 경우, Left outer Join으로 내부적으로 처리된다.
        //보편적인 코딩 가이드는 지연 로딩을 기본적으로 사용하고, 상황에 맞는 방법을 찾는다.
        /*
        * TODO: no Session
        * - 지연방식으로 로딩시, board 테이블만 가져오기 때문에, board.getWriter()를 가져오는데, 문제가발생 (이미 DB 연결은 끝났기에)
        * -> 이를 해결하기 위해 Transactional을 붙여준다.
        * */
        //org.hibernate.LazyInitializationException: could not initialize proxy [com.rumblekat.board.entity.Member#user100@aaa.com] - no Session

        /*
        *   select
        board0_.gno as gno1_0_0_,
        board0_.moddate as moddate2_0_0_,
        board0_.regdate as regdate3_0_0_,
        board0_.content as content4_0_0_,
        board0_.title as title5_0_0_,
        board0_.writer_email as writer_e6_0_0_,
        member1_.email as email1_1_1_,
        member1_.name as name2_1_1_,
        member1_.password as password3_1_1_
    from
        board board0_
    left outer join
        member member1_
            on board0_.writer_email=member1_.email
    where
        board0_.gno=?
        *
        * */
    }

    @Test
    public void testReadWithWriter(){
        Object result = boardRepository.getBoardWithWriter(100L);
        Object [] arr = (Object[]) result;

        System.out.println("--------------------------------------");
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testGetBoardwithReply(){
        List<Object[]> result = boardRepository.getBoardWithReply(100L);

        for(Object [] arr : result){
            System.out.println(Arrays.toString(arr));
        }
    }

    @Test
    public void testWithReplyCount(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());
        Page<Object []> result = boardRepository.getBoardWithReplyCount(pageable);
        result.get().forEach(row->{
            Object [] arr = (Object[]) row;
            System.out.println(Arrays.toString(arr));
        });
    }

    @Test
    public void testRead3(){
        Object result = boardRepository.getBoardByBno(100L);
        Object [] arr = (Object [])result;
        System.out.println(Arrays.toString(arr));
    }

    @Test
    public void testSearch1(){
        boardRepository.search1();
    }

    @Test
    public void testSearchPage(){
        Pageable pageable = PageRequest.of(0,10,
                Sort.by("gno").descending()
                        .and(Sort.by("title").ascending()));
        Page<Object[]> result = boardRepository.searchPage("t","1", pageable);
    }

}