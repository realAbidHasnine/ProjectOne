package com.Rush.ProjectOne.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequestDTO {
    
    @NotBlank(message = "Name Field can not be Empty!!")
    private String fullName;

    @NotBlank
    @Email(message = "Invalid Email Format!!")
    private String email;
    private String bio;
}
