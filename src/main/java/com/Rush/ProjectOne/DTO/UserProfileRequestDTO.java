package com.Rush.ProjectOne.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class UserProfileRequestDTO {
    private String avatarUrl;
    private String location;
    private String website;
    private LocalDate dateOfBirth;
}
