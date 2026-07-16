package com.Rush.ProjectOne.repository;

import com.Rush.ProjectOne.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, Long> {
}
