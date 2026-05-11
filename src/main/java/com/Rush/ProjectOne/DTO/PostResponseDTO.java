package com.Rush.ProjectOne.DTO;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class PostResponseDTO {
    private Long id;
    private String authorName;
    private String content;
    private LocalDateTime createdAt;
}
