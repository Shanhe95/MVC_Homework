package com.sparta.mvc_homework.service;

import com.sparta.mvc_homework.dto.PostRequestDto;
import com.sparta.mvc_homework.dto.PostResponseDto;
import com.sparta.mvc_homework.entity.Post;
import com.sparta.mvc_homework.entity.User;

import com.sparta.mvc_homework.jwt.JwtUtil;
import com.sparta.mvc_homework.repository.PostRepository;

import com.sparta.mvc_homework.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;             //5.데이터베이스와 연결을 해야하기 때문에 연결하는 부분인
    private final UserRepository userRepository;

    private final JwtUtil jwtUtil;

    //PostRepository를 사용할 수 있도록 인스턴스 변수 생성.
    //그리고 Service를 만들었기 때문에 다시 컨트롤러로 돌아가서.
    @Transactional
    public Post createPost(PostRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;    //사용자 정보

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Post post = postRepository.saveAndFlush(new Post(requestDto, user));                 //검증이 완료가 되면 new Product 에 requestDto, user.getId())가 들어간다
            //원래는 Product에 userid가 들어가지 않았기 떄문에 saveAndFlush를 했었는데(위에 주석 코드)
            //여기서는 Product와 user가 연관관계가 있다는 가정을 해서 Product쪽에 userid를 넣어줬기 때문에

            return post;
        }
        return null;
    }


    @Transactional   //(readOnly = true)
    public List<PostResponseDto> getPosts() {                                      //12.  조회기능 구현하러옴 (readOnly = true)읽기만 가능하게
        List<Post> postlist = postRepository.findAllByOrderByModifiedAtDesc();
        List<PostResponseDto> responseDtoList = new ArrayList<>();
        for (Post post : postlist) {
            responseDtoList.add(new PostResponseDto(post));
        }
        return responseDtoList;


        //postRepository를 연결해서 findAll()로 저장된 데이터를 가져올 수 있다
    }                                                                   //근데 포스트가 가장 최근에 등록된 포스트 순으로 보여주려고하기때문에

    public PostResponseDto getPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("아이디가 없습니다"));
        return new PostResponseDto(post);
    }


    @Transactional
    public PostResponseDto updatePost(Long postId, PostRequestDto requestDto, HttpServletRequest request) {
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;    //사용자 정보

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );

            // 해당 사용자가 작성한 게시글인지 확인하기 위해 DB에서 게시글 조회
            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
            );

            if (post.getUser().getId().equals(user.getId())) {
                post.update(requestDto);
            } else {
                throw new IllegalArgumentException("해당 게시글을 수정할 권한이 없습니다.");
            }
            return new PostResponseDto(post);
        }
        return null;
    }


//        Post post = postRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));
//        if (post.getPassword().equals(requestDto.getPassword())) {
//            post.update(requestDto);
//        } else {
//            throw new Exception("비밀번호가 일치하지 않습니다");
//        }
//        return post;


    public PostResponseDto deletePost(Long postId, PostRequestDto requestDto, HttpServletRequest request){
        // Request에서 Token 가져오기
        String token = jwtUtil.resolveToken(request);
        Claims claims;    //사용자 정보

        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
            );
            // 해당 사용자가 작성한 게시글인지 확인하기 위해 DB에서 게시글 조회
            Post post = postRepository.findById(postId).orElseThrow(
                    () -> new IllegalArgumentException("해당 게시글이 존재하지 않습니다.")
            );

            if (post.getUser().getId().equals(user.getId())) {
                postRepository.delete(post);
            } else {
                throw new IllegalArgumentException("해당 게시글을 삭제할 권한이 없습니다.");
            }
            return new PostResponseDto(post);
        }
        return null;
    }
    }
//        Post post = postRepository.findById(postId).orElseThrow(
//                () -> new IllegalArgumentException(""));
//        if (post.getPassword().equals(requestDto.getPassword())) {
//            postRepository.delete(post);
//        } else {
//            throw new Exception("비밀번호가 일치하지 않습니다");
//        }
//        return post;
//    }

//
//    public Post findOnePost(Long id) {
//        Post post = postRepository.findById(id).orElse(null);
//        return post;
//    }



