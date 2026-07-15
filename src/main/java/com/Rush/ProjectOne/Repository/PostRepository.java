package com.Rush.ProjectOne.repository;

import com.Rush.ProjectOne.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
    List<PostEntity> findByActiveTrue();
}
