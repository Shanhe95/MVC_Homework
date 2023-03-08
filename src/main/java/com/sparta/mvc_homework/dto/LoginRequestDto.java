package com.sparta.mvc_homework.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter
@Getter
public class LoginRequestDto {
    @Column
    private String username;
    @Column
    private String password;
}