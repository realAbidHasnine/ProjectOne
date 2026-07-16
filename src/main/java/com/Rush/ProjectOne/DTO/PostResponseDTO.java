package com.Rush.ProjectOne.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {
    private Long id;
    private String authorName;
    private String content;
    private LocalDateTime createdAt;
    private Long userId;
    private String userEmail;
}
