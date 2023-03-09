package com.sparta.mvc_homework.controller;


import com.sparta.mvc_homework.dto.PostRequestDto;
import com.sparta.mvc_homework.dto.PostResponseDto;
import com.sparta.mvc_homework.entity.Post;
import com.sparta.mvc_homework.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Validated
@RestController                                                       // 컨트롤러 + json형식으로 데이터를 받겠다~     (@Controller + @ResponseBody)
@RequiredArgsConstructor                                              //
                                                                      //@requestmapping("/hi")
public class PostController {

    private final PostService postService;                            //서비스를 불러오기 위해 작성


    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }


    @PostMapping("/api/post")            //게시글 생성                                                               //2.   ajax의 url과 같아야하지만, 없으니 url 대충
    public Post createPost(@RequestBody PostRequestDto requestDto, HttpServletRequest request) {         //Post 반환   //객체형식으로 넘어오기 때문에(dto) requestbody(dto받아올 때 씀)

        return postService.createPost(requestDto, request);                                                      //post 방식이기 때문에 body가 있음 그래서 requestbody                               //
                                                                                                        //  body안에 우리가 저장하고자 하는것들이 넘어오기 때문에 requestbody를 사용
    }                                                                                                    //3. 이제 dto만들 차례, 만들고오면 PostRequestDto 빨간불 사라짐

    @GetMapping("/api/posts")          //전체 게시글 조회
    public List<PostResponseDto> getPosts() {//타입 , 반환타입 , 메소드명
        return postService.getPosts();
    }

    @GetMapping("/api/post/{postId}")      //유저가 작성한 게시글 조회
    public PostResponseDto getPost(@PathVariable Long postId) {
        return postService.getPost(postId);
    }



    @PutMapping("/api/post/{postId}")
    public PostResponseDto updatePost(@PathVariable Long postId, @RequestBody PostRequestDto requestDto, HttpServletRequest request){
        System.out.println(postId+"==================");
        return postService.updatePost(postId, requestDto, request);
    }
//
//    @GetMapping("/api/find/")
//    public Post findOnePost(@PathVariable Long id){
//        return postService.findOnePost(id);
//    }



    @DeleteMapping("/api/delete/{postId}")
    public PostResponseDto deletePost(@PathVariable Long postId,@RequestBody PostRequestDto requestDto, HttpServletRequest request){
        return postService.deletePost(postId, requestDto, request);
    }


}