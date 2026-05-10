package com.Rush.ProjectOne.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Rush.ProjectOne.Entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

}
