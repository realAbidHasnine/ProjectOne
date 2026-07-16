package com.Rush.ProjectOne.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDTO {

    @NotBlank(message = "Content cannot be empty")
    @Size(max = 500, message = "Content must not exceed 500 characters")
    private String content;

    @NotNull(message = "Post ID is required")
    private Long postId;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
}
