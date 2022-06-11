package com.springboot.myrest.validator;

import com.springboot.myrest.model.Board;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.thymeleaf.util.StringUtils;
@Component
public class BoardValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Board.class.equals(clazz);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Board b = (Board) obj;
        if (StringUtils.isEmpty(b.getContent())) { // content 가 비어있을 경우
            errors.rejectValue("content", "key", "내용을 입력하세요");
        }
    }
}
