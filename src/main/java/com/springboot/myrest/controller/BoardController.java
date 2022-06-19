package com.springboot.myrest.controller;

import com.springboot.myrest.model.Board;
import com.springboot.myrest.repository.BoardRepository;
import com.springboot.myrest.validator.BoardValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private BoardValidator boardValidator;

    @GetMapping("/list")
    public String list(Model model, @PageableDefault(size = 2) Pageable pageable,
                       @RequestParam(required = false, defaultValue = "") String searchText) {
        // Page<Board> boards = boardRepository.findAll(pageable);
        // boards.getTotalElements(); // 총 개수 구하기
        Page<Board> boards = boardRepository.findByTitleContainingOrContentContaining(searchText, searchText, pageable);
        int startPage = Math.max(1, boards.getPageable().getPageNumber() - 4); // 최소 1페이지 부터 시작
        int endPage = Math.min(boards.getTotalPages(), boards.getPageable().getPageNumber() + 4);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/form")
    public String form(Model model, @RequestParam(required = false) Long id) { // id 값을 받아온다
        if (id == null) {
            model.addAttribute("board", new Board()); // id null 이면 새로 만든다
        } else {
            Board board = boardRepository.findById(id).orElse(null); // 값이 없으면 null
            model.addAttribute("board", board);
        }
        return "board/form";
    }

    @PostMapping("/form")
    public String formSubmit(@Valid Board board, BindingResult bindingResult) { // spring 이 not null, size 검사
        boardValidator.validate(board, bindingResult); // content 가 null 인지 확인
        if (bindingResult.hasErrors()) {
            return "board/form";
        }
        boardRepository.save(board); // id 의 키 값이 있으면 update 없으면 insert
        return "redirect:/board/list"; // redirect  해서 재 조회 한다
    }
}
