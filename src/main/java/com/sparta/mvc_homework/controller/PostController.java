package com.sparta.mvc_homework.controller;


import com.sparta.mvc_homework.dto.PostRequestDto;
import com.sparta.mvc_homework.entity.Post;
import com.sparta.mvc_homework.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    }              //ModelAndView ????????????????????????????????????


    @PostMapping("/api/post")                                             //2.   ajax의 url과 같아야하지만, 없으니 url 대충
    public Post createPost(@RequestBody PostRequestDto requestDto) {         //Post 반환   //객체형식으로 넘어오기 때문에(dto) requestbody(dto받아올 때 씀)
        return postService.createPost(requestDto);                           //post 방식이기 때문에 body가 있음 그래서 requestbody                               //
                                                                             //  body안에 우리가 저장하고자 하는것들이 넘어오기 때문에 requestbody를 사용
    }                                                                        //3. 이제 dto만들 차례, 만들고오면 PostRequestDto 빨간불 사라짐
                                                                            //4. PostRequestDto만들고 왔음 이제 서비스 만들러갈거임(postService)
                                                                            //7. service는 불러왔고, 이제 createPost구현하러 다시 서비스로 갈거


    @GetMapping("/api/posts")                                        //11. 조회기능 반환타입은 List<Post>
    public List<Post> getPosts() {                                      // 클라이언트에서 넘겨주는게 없기때문에 매개변수에는 아무것도 들어갈 필요가 없다
        return postService.getPosts();                                  // 그리고 서비스에서 구현할 함수명 대충 쓰고 서비스로 ㄱㄱ(빨간불에 갖다대면 생성가능)
    }


    @PutMapping("/api/posts/{id}")
    public Post updatePost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) throws Exception {
        return postService.updatePost(id, requestDto);
    }

    @GetMapping("/api/find/{id}")
    public Post findOnePost(@PathVariable Long id){
        return postService.findOnePost(id);
    }


    @DeleteMapping("/api/delete/{id}")
    public Post deletePost(@PathVariable Long id,@RequestBody PostRequestDto requestDto)throws Exception {
        return postService.deletePost(id, requestDto);
    }
}