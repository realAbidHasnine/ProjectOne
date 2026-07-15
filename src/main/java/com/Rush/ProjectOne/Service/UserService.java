package com.Rush.ProjectOne.service;

import com.Rush.ProjectOne.dto.BioUpdateRequestDTO;
import com.Rush.ProjectOne.dto.UserRequestDTO;
import com.Rush.ProjectOne.dto.UserResponseDTO;
import com.Rush.ProjectOne.entity.UserEntity;
import com.Rush.ProjectOne.exception.DuplicateEmailException;
import com.Rush.ProjectOne.exception.ResourceNotFoundException;
import com.Rush.ProjectOne.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO request) {
 
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException(request.getEmail());
        }

        UserEntity entity = UserEntity.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .bio(request.getBio())
                .build();

        UserEntity saved = userRepository.save(entity);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));
        return toResponse(entity);
    }

    @Transactional
    public UserResponseDTO updateBio(Long id, BioUpdateRequestDTO request) {
        UserEntity entity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", id));

        entity.setBio(request.getBio());
        UserEntity updated = userRepository.save(entity);
        return toResponse(updated);
    }

    private UserResponseDTO toResponse(UserEntity entity) {
        return UserResponseDTO.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .bio(entity.getBio())
                .build();
    }
}
