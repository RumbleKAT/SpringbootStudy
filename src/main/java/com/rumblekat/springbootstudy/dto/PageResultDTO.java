package com.rumblekat.springbootstudy.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/*
* JPA를 이용하는 Repository 에서는 페이지 처리 결과를 Page<Entity> 타입으로 반환
* 서비스 계층에서 이를 처리하기 위해 별도 클래스를 만들어 처리해야됨 -> Page<Entity>의 객체들을 DTO로 변환해서 자료구조로 담아야된다.
* */

@Data
public class PageResultDTO<DTO, EN> {
    private List<DTO> dtoList;
    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){
        dtoList = result.stream().map(fn).collect(Collectors.toList()); //Entity를 DTO로 변환한다.
    }

}
