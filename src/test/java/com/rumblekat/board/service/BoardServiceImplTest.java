package com.rumblekat.board.service;

import com.rumblekat.board.dto.BoardDTO;
import com.rumblekat.board.dto.PageRequestDTO;
import com.rumblekat.board.dto.PageResultDTO;
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

}