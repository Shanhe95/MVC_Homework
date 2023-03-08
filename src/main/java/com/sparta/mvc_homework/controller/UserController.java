package com.sparta.mvc_homework.controller;

import com.sparta.mvc_homework.dto.LoginRequestDto;
import com.sparta.mvc_homework.dto.SignupRequestDto;
import com.sparta.mvc_homework.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor          //service 연결하려면 이게 필요하다
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;      //의존성 주입


    @GetMapping("/signup")
    public ModelAndView signupPage() {
        return new ModelAndView("signup");
    }


    @PostMapping("/signup")
    public String signup(SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return "redirect:/api/user/login";
    }




    @GetMapping("/login")
    public ModelAndView loginPage() {
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public String login(LoginRequestDto loginRequestDto,  HttpServletResponse response) {
        userService.login(loginRequestDto,response);
        return "redirect:/api/post";
    }

}
