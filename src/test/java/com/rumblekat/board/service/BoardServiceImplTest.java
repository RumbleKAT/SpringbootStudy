package com.rumblekat.board.service;

import com.rumblekat.board.dto.BoardDTO;
import com.rumblekat.board.dto.PageRequestDTO;
import com.rumblekat.board.dto.PageResultDTO;
import com.rumblekat.board.entity.Board;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceImplTest {
    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){

        BoardDTO dto = BoardDTO.builder()
                .title("Test...")
                .content("Test....")
                .writerEmail("user55@aaa.com")
                .build();
        Long bno = boardService.register(dto);

    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = new PageRequestDTO();
        PageResultDTO<BoardDTO,Object[]> result = boardService.getList(pageRequestDTO);
        for(BoardDTO boardDTO: result.getDtoList()){
            System.out.println(boardDTO);
        }
    }

    @Test
    public void testGet(){
        Long bno = 100L;

        BoardDTO boardDTO = boardService.get(bno);

        System.out.println(boardDTO);
    }

    @Test
    public void testRemove(){
        Long bno = 1L;
        boardService.removeWithReplies(bno);
//        Hibernate:
//        delete
//                from
//        reply
//                where
//        board_gno=?
//        Hibernate:
//        select
//        board0_.gno as gno1_0_0_,
//                board0_.moddate as moddate2_0_0_,
//        board0_.regdate as regdate3_0_0_,
//                board0_.content as content4_0_0_,
//        board0_.title as title5_0_0_,
//                board0_.writer_email as writer_e6_0_0_
//        from
//        board board0_
//        where
//        board0_.gno=?
//        Hibernate:
//        delete
//                from
//        board
//                where
//        gno=?
    }

    @Test
    public void testModify(){
        BoardDTO boardDTO = BoardDTO.builder()
                .bno(2L)
                .title("change title.")
                .content("change content.")
                .build();
        boardService.modify(boardDTO);
    }


}