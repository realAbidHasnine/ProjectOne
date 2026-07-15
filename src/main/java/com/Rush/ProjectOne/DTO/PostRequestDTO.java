package com.Rush.ProjectOne.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequestDTO {

    @NotBlank(message = "Author name is required")
    private String authorName;

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 280, message = "Content must not exceed 280 characters")
    private String content;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
}
