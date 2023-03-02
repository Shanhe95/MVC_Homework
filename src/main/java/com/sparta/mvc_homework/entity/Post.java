package com.sparta.mvc_homework.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import com.sparta.mvc_homework.dto.PostRequestDto;

@Getter
@Entity
@NoArgsConstructor
public class Post extends Timestamped {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String content;

    public Post(PostRequestDto requestDto) {            //다른데서 쓸 수 있게끔 생성자 생성해서 dto 활성화.
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.password =requestDto.getPassword();
        this.content =requestDto.getContent();
    }

    public void update(PostRequestDto requestDto) {     //업데이트하기 위한 생성자 (id 추가)
        this.title = requestDto.getTitle();
        this.author = requestDto.getAuthor();
        this.password =requestDto.getPassword();
        this.content =requestDto.getContent();
    }
}
