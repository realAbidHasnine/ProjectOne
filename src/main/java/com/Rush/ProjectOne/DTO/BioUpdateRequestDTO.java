package com.Rush.ProjectOne.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BioUpdateRequestDTO {

    @NotBlank(message = "Bio cannot be empty")
    private String bio;
}
