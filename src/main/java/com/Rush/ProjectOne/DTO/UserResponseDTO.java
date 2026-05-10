package com.Rush.ProjectOne.DTO;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String bio;
}
