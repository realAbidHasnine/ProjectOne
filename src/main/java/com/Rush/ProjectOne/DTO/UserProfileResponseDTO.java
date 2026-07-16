package com.Rush.ProjectOne.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponseDTO {
    private Long userId;
    private String fullName;
    private String email;
    private String avatarUrl;
    private String location;
    private String website;
    private LocalDate dateOfBirth;
}
