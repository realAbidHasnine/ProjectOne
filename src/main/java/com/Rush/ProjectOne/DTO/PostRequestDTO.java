package com.Rush.ProjectOne.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequestDTO {

    @NotBlank(message = "Author Name is Required")
    private String authorName;

    @NotBlank(message = "Content can not be empty")
    @Size(max = 280)
    private String content;

    @NotBlank(message = "Email is Required")
    @Email(message = "Invalid Email Format")
    private String email;
}
