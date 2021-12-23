package com.rumblekat.springbootstudy.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {
    private int page;
    private int size;

    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
        //기본값 세팅
    }

    public Pageable getPageable(Sort sort){
        //페이지가 0부터 시작하는 것을 감안해서, 정렬은 다양한 상황에 받을수 있게 별도의 파라미터 처리
        return PageRequest.of(page-1, size,sort);
    }
}
