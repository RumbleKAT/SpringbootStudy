package com.rumblekat.springbootstudy.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
* JPA를 이용하는 Repository 에서는 페이지 처리 결과를 Page<Entity> 타입으로 반환
* 서비스 계층에서 이를 처리하기 위해 별도 클래스를 만들어 처리해야됨 -> Page<Entity>의 객체들을 DTO로 변환해서 자료구조로 담아야된다.
*
* 현재 1page인 경우,tempEnd = (int)(Math.ceil(페이지번호/10))*10;
* start = tempEnd - 1;
* totalPage = result.getTotalPages();
* end = totalPage > tempEnd ? tempEnd : totalPage;
*
* */

@Data
public class PageResultDTO<DTO, EN> {
    //DTO 리스트
    private List<DTO> dtoList;
    //총 페이지 번호
    private int totalPage;
    //현재 페이지 번호
    private int page;
    //목록 사이즈
    private int size;
    //시작 페이지 번호, 끝 페이지 번호
    private int start, end;
    //이전, 다음
    private boolean prev,next;
    //페이지 번호 목록
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList()); //Entity를 DTO로 변환한다.
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1; //0부터 시작하므로 1을 추가한다.
        this.size = pageable.getPageSize();

        //temp end Page
        int tempEnd = (int)(Math.ceil(page/10.0)) * 10;
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;
        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
