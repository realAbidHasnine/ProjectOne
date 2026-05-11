package com.Rush.ProjectOne.Service;

import org.springframework.stereotype.Service;

import com.Rush.ProjectOne.DTO.UserRequestDTO;
import com.Rush.ProjectOne.DTO.UserResponseDTO;
import com.Rush.ProjectOne.Entity.UserEntity;
import com.Rush.ProjectOne.Repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    public UserEntity toEntity(UserRequestDTO userReqDTO) {

        UserEntity userEntity = new UserEntity();

        userEntity.setFullName(userReqDTO.getFullName());
        userEntity.setEmail(userReqDTO.getEmail());
        userEntity.setBio(userReqDTO.getBio());

        return userEntity;
    }

    public UserResponseDTO toResponse(UserEntity entity) {

        UserResponseDTO resDTO = new UserResponseDTO();

        resDTO.setId(entity.getId());
        resDTO.setEmail(entity.getEmail());
        resDTO.setFullName(entity.getFullName());
        resDTO.setBio(entity.getBio());

        return resDTO;
    }

    public UserResponseDTO createUser(UserRequestDTO userReqDTO) {

        if (userRepo.findByEmail(userReqDTO.getEmail()).isPresent()) {
            throw new RuntimeException("Email Already in Use");
        }

        UserEntity entity = toEntity(userReqDTO);
        UserEntity saveEntity = userRepo.save(entity);

        return toResponse(saveEntity);
    }

    public UserResponseDTO getUserById(Long id) {
        UserEntity entity = userRepo.findById(id).orElse(null);

        if (entity == null) {
            throw new RuntimeException("User not Found!!");
        }

        return toResponse(entity);
    }

    public UserResponseDTO updateBio(Long id, String bio) {
        UserEntity entity = userRepo.findById(id).orElse(null);

        if (entity == null) {
            throw new RuntimeException("User not Found to Upadte Bio");
        }

        UserEntity updatedEntity = userRepo.save(entity);
        return toResponse(updatedEntity);
    }
}
