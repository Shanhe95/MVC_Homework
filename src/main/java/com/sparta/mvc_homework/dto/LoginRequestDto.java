package com.sparta.mvc_homework.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginRequestDto {

    private String username;
    private String password;
}