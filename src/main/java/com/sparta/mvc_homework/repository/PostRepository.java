package com.sparta.mvc_homework.repository;


import com.sparta.mvc_homework.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {   // JpaRepository 상속 받아야 만들어진 메서드 쓸 수 있음

    List<Post> findAllByOrderByModifiedAtDesc();
}
