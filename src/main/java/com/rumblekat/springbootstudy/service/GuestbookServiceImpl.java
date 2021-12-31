package com.rumblekat.springbootstudy.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.rumblekat.springbootstudy.dto.GuestbookDTO;
import com.rumblekat.springbootstudy.dto.PageRequestDTO;
import com.rumblekat.springbootstudy.dto.PageResultDTO;
import com.rumblekat.springbootstudy.guestbook.entity.Guestbook;
import com.rumblekat.springbootstudy.guestbook.entity.QGuestbook;
import com.rumblekat.springbootstudy.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor //의존성 자동 주입
public class GuestbookServiceImpl implements GuestbookService{

    private final GuestbookRepository repository;

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO-------------------------");
        log.info(dto);

        Guestbook entity = dtoToEntity(dto);

        log.info(entity);
        repository.save(entity);

        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        BooleanBuilder booleanBuilder = getSearch(requestDTO); //검색 조건을 처리한다.

        Page<Guestbook> result = repository.findAll(booleanBuilder,pageable); //querydsl을 사용

        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result,fn);
    }

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);
        //Entity 객체를 가져온후, DTO로 변환한다.
        return result.isPresent()? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        log.error("check : " + gno);
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        //업데이트 하는 항목은 '제목', '내용'
        Optional<Guestbook> result = repository.findById(dto.getGno());
        if(result.isPresent()){
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity);
        }
        /*
        * 수정작업 후 이동처리
        * 등록, 삭제처리 : 1페이지로 리디렉션
        * 수정처리 : 조회페이지로 이동
        * */

    }

    private BooleanBuilder getSearch(PageRequestDTO requestDTO){ //queryDSL 처리
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestbook qGuestbook = QGuestbook.guestbook;

        String keyword = requestDTO.getKeyword();
        BooleanExpression expression = qGuestbook.gno.gt(0L); //gno > 0

        booleanBuilder.and(expression);

        if(type == null || type.trim().length() == 0){
            return booleanBuilder;
        }

        //검색 조건 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        if(type.contains("t")){
            conditionBuilder.or(qGuestbook.title.contains(keyword));
        }
        if(type.contains("c")){
            conditionBuilder.or(qGuestbook.content.contains(keyword));
        }
        if(type.contains("w")){
            conditionBuilder.or(qGuestbook.writer.contains(keyword));
        }

        //모든 조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }


}
