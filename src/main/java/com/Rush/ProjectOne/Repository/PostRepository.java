package com.Rush.ProjectOne.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.Rush.ProjectOne.Entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity,Long> {
    List<PostEntity> findByActiveTrue();
} 
