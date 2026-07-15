package com.Rush.ProjectOne.controller;

import com.Rush.ProjectOne.dto.BioUpdateRequestDTO;
import com.Rush.ProjectOne.dto.UserRequestDTO;
import com.Rush.ProjectOne.dto.UserResponseDTO;
import com.Rush.ProjectOne.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    //declare service
    private final UserService userService;

    //constructor
    public UserController(UserService userService) {
        this.userService = userService;
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
    public ResponseEntity<UserResponseDTO> updateBio(
            @PathVariable Long id,
            @Valid @RequestBody BioUpdateRequestDTO request) {
        UserResponseDTO response = userService.updateBio(id, request);
        return ResponseEntity.ok(response);
    }
}
