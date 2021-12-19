package com.rumblekat.springbootstudy.repository;

import com.rumblekat.springbootstudy.entity.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoRepositoryTest {

    @Autowired
    MemoRepository memoRepository;

    @Test
    public void testClass(){
        System.out.println(memoRepository.getClass().getName());
    }

    @Test
    public void testInsertDummies(){
        IntStream.rangeClosed(1,100).forEach(i -> {
            Memo memo = Memo.builder().memoText("Sample..."+i).build();
            memoRepository.save(memo);
        });
    }

    @Test
    public void testSelect(){
        Long mno = 100L;
        Optional<Memo> result = memoRepository.findById(mno);
        System.out.println("=======================================");
        if(result.isPresent()){
            Memo memo = result.get();
            System.out.println(memo);
        }
    }

    @Transactional
    @Test
    public void testSelect2(){
        //getOne = 실제 객체가 필요한 순간까지 select을 하지 않는다.
        Long mno = 100L;
        Memo memo = memoRepository.getOne(mno);
        System.out.println("=======================================");
        System.out.println(memo);
    }

    @Test
    public void testUpdate(){
        Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
        System.out.println(memoRepository.save(memo));
        /*
        * JPA는 에닡티 객체들을 메모리상에 보관하려고 하기에, 특정 엔티티가 존재하는지 확인하는 select가 먼저 실행되고 해당 @ID가 있는 엔티티가 있으면 update, 없으면 Insert를 한다.
        *
        * */
    }

    @Test
    public void testDelete(){
        Long mno = 100L;
        memoRepository.deleteById(mno);
    }

    @Test
    public void testPageDefault(){
        Pageable pageable = PageRequest.of(0,10);
        Page<Memo> result = memoRepository.findAll(pageable);
        System.out.println(result);
        /*
        * Page 타입의 경우, 단순 해당 목록만 가져오는 것이 아닌, 실제 페이지 처리에 필요한 전체 데이터의 개수를 가져오는 쿼리 역시 같이 처리
        *
        * */
        System.out.println("--------------------------------------");
        System.out.println("Total Pages : " + result.getTotalPages());
        System.out.println("Total Count : " + result.getTotalElements());
        System.out.println("Page Number : " + result.getNumber());
        System.out.println("Page Size : " + result.getNumber());
        System.out.println("has next Page ? : " + result.hasNext());
        System.out.println("First Page ? : " + result.isFirst());
    }

    @Test
    public void testSort(){
        Sort sort1 = Sort.by("mno").descending();
        Sort sort2 = Sort.by("memoText").ascending();
        Sort sortAll = sort1.and(sort2);
        Pageable pageable = PageRequest.of(0,10, sortAll);
        Page<Memo> result = memoRepository.findAll(pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Test
    public void testQueryMethods(){
        List<Memo> list = memoRepository.findByMnoBetweenOrderByMnoDesc(70L,80L);
        for(Memo memo:list){
            System.out.println(memo);
        }
    }

    @Test
    public void testQueryMethodWithPagable(){
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
        Page<Memo> result = memoRepository.findByMnoBetween(10L,50L,pageable);
        result.get().forEach(memo -> {
            System.out.println(memo);
        });
    }

    @Commit
    @Transactional
    @Test
    public void testDeleteQueryMethods(){
        memoRepository.deleteMemoByMnoLessThan(10L);
        /*
        * deleteBy의 경우, 우선은 select문으로 해당 엔티티 객체들을 가져오는 작업과, 각 엔티티를 삭제하는 작업이 같이 이루어지기에, Transactional 필수
        * commit은 최종 결과를 커밋하기위해 사용됨, deleteBy는 기본적으로 롤백 처리되어서 결과가 반영되지 않는다.
        * --> 실제로 잘 사용되지 않는데, row 단위로 삭제하기 때문.. => @Query를 이용하여 비효율적인 부분을 개선한다.
        * */
    }
}