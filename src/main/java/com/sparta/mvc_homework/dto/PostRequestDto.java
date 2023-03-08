package com.sparta.mvc_homework.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
@Getter
public class PostRequestDto { //클라이언트로부터 넘어오는 데이터를 저장해 다른곳에서 객체로 사용할 수 있게 해주는 역할
                                //    @NotBlank(message = "아이디를 입력해 주세요")
    private String title;
    private String author;
    private String password;
    private String content;
}
