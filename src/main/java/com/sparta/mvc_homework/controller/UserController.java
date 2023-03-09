package com.sparta.mvc_homework.controller;

import com.sparta.mvc_homework.dto.LoginRequestDto;
import com.sparta.mvc_homework.dto.SignupRequestDto;
import com.sparta.mvc_homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor          //service 연결하려면 이게 필요하다
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;      //의존성 주입


    @GetMapping("/signup")                           //회원가입 화면
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }


    @ResponseBody
    @PostMapping("/signup")                         //회원가입 기능
    public ResponseEntity<String> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return ResponseEntity.ok("회원가입 성공");
    }



    @GetMapping("/login")                           //로그인 화면
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @ResponseBody           //안붙으면 html로 보냄,
    @PostMapping("/login")                         //로그인 기능
    public ResponseEntity login(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto,response);
//        return "redirect:/api/post";
        return ResponseEntity.ok("로그인 성공");
    }

}