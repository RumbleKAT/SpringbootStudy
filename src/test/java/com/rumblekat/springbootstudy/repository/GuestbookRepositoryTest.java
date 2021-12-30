package com.rumblekat.springbootstudy.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.rumblekat.springbootstudy.dto.GuestbookDTO;
import com.rumblekat.springbootstudy.dto.PageRequestDTO;
import com.rumblekat.springbootstudy.dto.PageResultDTO;
import com.rumblekat.springbootstudy.guestbook.entity.Guestbook;
import com.rumblekat.springbootstudy.guestbook.entity.QGuestbook;
import com.rumblekat.springbootstudy.service.GuestbookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTest {
    @Autowired
    private GuestbookRepository guestbookRepository;

    @Autowired
    private GuestbookService service;

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

    @Test
    public void updateTest(){
        Optional<Guestbook> result = guestbookRepository.findById(300L);
        if(result.isPresent()){
            Guestbook guestbook = result.get();
            guestbook.changeTitle("Changed Title....");
            guestbook.changeContent("Changed Content....");
            guestbookRepository.save(guestbook);
        }
    }

    @Test
    public void testQuery1(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").descending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        //1. 동적으로 처리하기 위해 Q도메인 클래스를 얻어온다. Q도메인 클래스를 이용하면 엔티티 클래스에 선언된 title, content를 사용 가능

        String keyword = "1";   //단일항목 검색 테스트

        BooleanBuilder builder = new BooleanBuilder();
        //where문에 들어가는 조건들을 넣어주는 컨테이너

        BooleanExpression expression = qGuestbook.title.contains(keyword);
        //원하는 조건은 필드 값과 같이 결합해서 생성한다.

        builder.and(expression);
        //만들어진 조건은 where 문에 and 나 or 같은 키워드와 결합한다.

        Page<Guestbook> result = guestbookRepository.findAll(builder,pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    @Test
    public void testQuery2(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("gno").descending());

        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = "1";

        BooleanBuilder builder = new BooleanBuilder();

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.content.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent);

        builder.and(exAll);
        builder.and(qGuestbook.gno.gt(0L));//gno가 0보다 크다

        Page<Guestbook> result = guestbookRepository.findAll(builder,pageable);
        result.stream().forEach(guestbook -> {
            System.out.println(guestbook);
        });
    }

    /*
    * 웹 어플리케이션을 제작할 땐, HttpServletRequest나 HttpServletResponse를 서비스 계층으로 전달하지 않는 것을 원칙으로한다.
    * 유사하게 엔티티 객체가 JPA에서 사용하는 객체이므로 JPA 외에서 사용하지 않는 것을 권장한다.
    *
    * DTO를 사용하는 경우, 가장 큰 단점은 Entity와 유사한 코드를 중복을 개발, 엔티티 객체를 DTO로 변환하거나 객체 변환의 과정이 필요.
    * */

    @Test
    public void testSearch(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .type("tc")
                .keyword("한글")
                .build();
         //http://localhost:8080/guestbook/list?page=1&type=t&keyword=11
         //TODO : 검색 속도를 더 높일 수 있는 방안? -> redis 적용
         /*
            TODO: ALTER TABLE table CONVERT TO CHARACTER SET utf8 COLLATE utf8_general_ci
             -> SQL Like문 수행시, UTF8 형식으로 저장되어 있지 않아서 데이터 컨버전 진행
         */

        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);
        System.out.println("PREV : " + resultDTO.isPrev());
        System.out.println("NEXT : " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());

        System.out.println("--------------------------------------------------");
        for(GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
        System.out.println("==================================================");
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}
