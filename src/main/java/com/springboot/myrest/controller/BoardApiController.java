package com.springboot.myrest.controller;

import com.springboot.myrest.model.Board;
import com.springboot.myrest.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.sql.DataSource;
import java.util.List;

// resfulapi 이용해서 CRUD 해보기 , PostMan 사용하면 편하다
@RestController
@RequestMapping("/api")
public class BoardApiController {
    @Autowired
    private BoardRepository repository;



    // Aggregate root
    @GetMapping("/boards")
    // Read
    List<Board> all(@RequestParam(required = false, defaultValue = "") String title,
                    @RequestParam(required = false, defaultValue = "") String content) {
        if (StringUtils.isEmpty(title) && StringUtils.isEmpty(content)) {
            return repository.findAll();
        } else {
            return repository.findByTitleOrContent(title, content);
        }

    }

    @PostMapping("/boards")
        // Create
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // Single item
    @GetMapping("/boards/{id}")
    // Read
    Board one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/boards/{id}")
        // Update
    Board replaceBoard(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id).map(board -> {
            board.setTitle(newBoard.getTitle());
            board.setContent(newBoard.getContent());
            return repository.save(board);
        }).orElseGet(() -> {
            newBoard.setId(id);
            return repository.save(newBoard);
        });
    }

    @DeleteMapping("/boards/{id}")
        // Delete
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }


}
