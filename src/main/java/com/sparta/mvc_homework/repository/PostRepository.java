package com.sparta.mvc_homework.repository;


import com.sparta.mvc_homework.dto.PostRequestDto;
import com.sparta.mvc_homework.entity.Post;
import com.sparta.mvc_homework.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {   // JpaRepository 상속 받아야 만들어진 메서드 쓸 수 있음

    List<Post> findAllByOrderByModifiedAtDesc();
}
