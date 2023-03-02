package com.sparta.mvc_homework.service;

import com.sparta.mvc_homework.dto.PostRequestDto;
import com.sparta.mvc_homework.entity.Post;
import com.sparta.mvc_homework.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;             //5.데이터베이스와 연결을 해야하기 때문에 연결하는 부분인

                                                            //PostRepository를 사용할 수 있도록 인스턴스 변수 생성.
                                                            //그리고 Service를 만들었기 때문에 다시 컨트롤러로 돌아가서.
    @Transactional
    public Post createPost(PostRequestDto requestDto) {     //8. 데이터베이스에 연결해서 저장하려면 @Entity에 있는 Post클래스를 인스턴스로 만들어서
        Post post = new Post(requestDto);                   //   그 값을 사용해서 저장해야 함. 그래서 Post 객체를 만들어주고 Post(Entity)에 있는
        postRepository.save(post);                          //    생성자를 사용해서 값들을 넣어준다.   이 생성자는 Post에서 만들어졌음.(안만들면 객체에 못담는다 에러남)
        return post;                                        //10. postRepository.save(post) 함수를 사용해서 함수안에 Post인스턴스를 넣어주면
    }                                                       //     자동으로 쿼리가 생성되고 디비에 연결되면서 저장된다. 그리고 Post를 반환.

    @Transactional   //(readOnly = true)
    public List<Post> getPosts() {                                      //12.  조회기능 구현하러옴 (readOnly = true)읽기만 가능하게
        return postRepository.findAllByOrderByModifiedAtDesc();         //postRepository를 연결해서 findAll()로 저장된 데이터를 가져올 수 있다
    }                                                                   //근데 포스트가 가장 최근에 등록된 포스트 순으로 보여주려고하기때문에
                                                                        //findAllByOrderByModifiedAtDesc()을 써야하는데
                                                                        //repository에서 설정을 해야함. 설정하러 ㄱㄱ


    @Transactional
    public Post updatePost(Long id, PostRequestDto requestDto) throws Exception {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
        if (post.getPassword().equals(requestDto.getPassword())) {
            post.update(requestDto);
        } else {
            throw new Exception("비밀번호가 일치하지 않습니다");
        }
        return post;
    }

    public Post deletePost(Long id, PostRequestDto requestDto) throws Exception{
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException(""));
        if (post.getPassword().equals(requestDto.getPassword())) {
            postRepository.delete(post);
        } else {
            throw new Exception("비밀번호가 일치하지 않습니다");
        }
        return post;
    }

    public Post findOnePost(Long id) {
        Post post = postRepository.findById(id).orElse(null);
        return post;
    }

}
