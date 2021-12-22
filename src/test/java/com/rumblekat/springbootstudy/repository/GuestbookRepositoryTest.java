package com.rumblekat.springbootstudy.repository;

import com.rumblekat.springbootstudy.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTest {
    @Autowired
    private GuestbookRepository guestbookRepository;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,300).forEach(i -> {
            Guestbook guestbook = Guestbook.builder()
                    .title("Title...."+i)
                    .content("Content...."+i)
                    .writer("user"+(i%10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }

    /*
    * regdate와 moddate 칼럼이 들어가지 않을땐
    * BaseEntity의 내용확인
    * Geuestbook 클래스의 상속 선언 부분
    * @EnableJpaAuditing의 적용여부
    * */
}
