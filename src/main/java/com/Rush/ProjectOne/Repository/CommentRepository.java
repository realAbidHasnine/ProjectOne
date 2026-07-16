package com.Rush.ProjectOne.repository;

import com.Rush.ProjectOne.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPostIdAndActiveTrue(Long postId);
}
