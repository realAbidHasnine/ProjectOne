package com.Rush.ProjectOne.service;

import com.Rush.ProjectOne.dto.*;
import com.Rush.ProjectOne.entity.UserEntity;
import com.Rush.ProjectOne.entity.UserProfileEntity;
import com.Rush.ProjectOne.exception.DuplicateEmailException;
import com.Rush.ProjectOne.exception.ResourceNotFoundException;
import com.Rush.ProjectOne.repository.UserProfileRepository;
import com.Rush.ProjectOne.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    public UserService(UserRepository userRepository, UserProfileRepository userProfileRepository) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
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

    @Transactional
    public UserProfileResponseDTO createOrUpdateProfile(Long userId, UserProfileRequestDTO request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        UserProfileEntity profile = user.getProfile();
        if (profile == null) {
            profile = UserProfileEntity.builder()
                    .user(user)
                    .avatarUrl(request.getAvatarUrl())
                    .location(request.getLocation())
                    .website(request.getWebsite())
                    .dateOfBirth(request.getDateOfBirth())
                    .build();
            user.setProfile(profile);
        } else {
            profile.setAvatarUrl(request.getAvatarUrl());
            profile.setLocation(request.getLocation());
            profile.setWebsite(request.getWebsite());
            profile.setDateOfBirth(request.getDateOfBirth());
        }

        userProfileRepository.save(profile);
        return toProfileResponse(user, profile);
    }

    @Transactional(readOnly = true)
    public UserProfileResponseDTO getProfile(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        UserProfileEntity profile = user.getProfile();
        if (profile == null) {
            throw new ResourceNotFoundException("Profile", userId);
        }

        return toProfileResponse(user, profile);
    }

    @Transactional
    public void followUser(Long userId, Long targetUserId) {
        if (userId.equals(targetUserId)) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        UserEntity target = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", targetUserId));

        if (user.getFollowing().contains(target)) {
            return;
        }

        user.getFollowing().add(target);
        userRepository.save(user);
    }

    @Transactional
    public void unfollowUser(Long userId, Long targetUserId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));
        UserEntity target = userRepository.findById(targetUserId)
                .orElseThrow(() -> new ResourceNotFoundException("User", targetUserId));

        user.getFollowing().remove(target);
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getFollowers(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        return user.getFollowers().stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<UserResponseDTO> getFollowing(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId));

        return user.getFollowing().stream()
                .map(this::toResponse)
                .toList();
    }

    private UserResponseDTO toResponse(UserEntity entity) {
        return UserResponseDTO.builder()
                .id(entity.getId())
                .fullName(entity.getFullName())
                .email(entity.getEmail())
                .bio(entity.getBio())
                .build();
    }

    private UserProfileResponseDTO toProfileResponse(UserEntity user, UserProfileEntity profile) {
        return UserProfileResponseDTO.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .avatarUrl(profile.getAvatarUrl())
                .location(profile.getLocation())
                .website(profile.getWebsite())
                .dateOfBirth(profile.getDateOfBirth())
                .build();
    }
}
