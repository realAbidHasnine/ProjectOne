package com.Rush.ProjectOne.controller;

import com.Rush.ProjectOne.dto.*;
import com.Rush.ProjectOne.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        List<UserResponseDTO> response = userService.getAllUsers();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request) {
        UserResponseDTO response = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        UserResponseDTO response = userService.getUserById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateBio(@PathVariable Long id, @Valid @RequestBody BioUpdateRequestDTO request) {
        UserResponseDTO response = userService.updateBio(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/profile")
    public ResponseEntity<UserProfileResponseDTO> getProfile(@PathVariable Long id) {
        UserProfileResponseDTO response = userService.getProfile(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<UserProfileResponseDTO> createOrUpdateProfile(
            @PathVariable Long id, @Valid @RequestBody UserProfileRequestDTO request) {
        UserProfileResponseDTO response = userService.createOrUpdateProfile(id, request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/follow/{targetId}")
    public ResponseEntity<Void> followUser(@PathVariable Long id, @PathVariable Long targetId) {
        userService.followUser(id, targetId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/follow/{targetId}")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long id, @PathVariable Long targetId) {
        userService.unfollowUser(id, targetId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/followers")
    public ResponseEntity<List<UserResponseDTO>> getFollowers(@PathVariable Long id) {
        List<UserResponseDTO> response = userService.getFollowers(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/following")
    public ResponseEntity<List<UserResponseDTO>> getFollowing(@PathVariable Long id) {
        List<UserResponseDTO> response = userService.getFollowing(id);
        return ResponseEntity.ok(response);
    }
}
