package com.rumblekat.springbootstudy.controller;

import com.rumblekat.springbootstudy.dto.PageRequestDTO;
import com.rumblekat.springbootstudy.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor //자동 주입을 위한 Annotation
public class GuestbookController {

    private final GuestbookService service; //final로 선언한다.

    @GetMapping("/")
    public String index(){
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list..........." + pageRequestDTO);
        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    /*
    * @PageableDefault라는 어노테이션으로 Pageable 타입을 이용할수 있고
    * application.properties에 0이 아닌 1부터 페이지 번호를 받을 수 있음
    *
    * */
}
