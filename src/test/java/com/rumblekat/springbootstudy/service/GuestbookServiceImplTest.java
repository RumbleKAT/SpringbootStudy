package com.rumblekat.springbootstudy.service;

import com.rumblekat.springbootstudy.dto.GuestbookDTO;
import com.rumblekat.springbootstudy.dto.PageRequestDTO;
import com.rumblekat.springbootstudy.dto.PageResultDTO;
import com.rumblekat.springbootstudy.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GuestbookServiceImplTest {

    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister(){

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title.....")
                .content("Sample Content.....")
                .writer("user0")
                .build();
        System.out.println(service.register(guestbookDTO));
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);
        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }

        System.out.println("PREV : " + resultDTO.isPrev());
        System.out.println("NEXT : " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());

        System.out.println("-----------------------------------");
        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }

        System.out.println("===================================");
        resultDTO.getDtoList().forEach(i-> System.out.println(i));

    }

}