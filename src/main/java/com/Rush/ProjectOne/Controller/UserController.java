package com.Rush.ProjectOne.Controller;

import java.util.Map;
import org.springframework.web.bind.annotation.*;
import com.Rush.ProjectOne.DTO.UserRequestDTO;
import com.Rush.ProjectOne.DTO.UserResponseDTO;
import com.Rush.ProjectOne.Service.UserService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping
    public UserResponseDTO createUSER(@Valid @RequestBody UserRequestDTO userReqDTO){
        return userService.createUser(userReqDTO); 
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUSERBYID(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public UserResponseDTO updateBIO(@PathVariable Long id,@RequestBody Map<String,String> userBody){
        String updated_bio = userBody.get("bio");
        return userService.updateBio(id, updated_bio);
    }
}
