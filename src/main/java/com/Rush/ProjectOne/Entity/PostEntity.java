package com.Rush.ProjectOne.Entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authorName;

    @Column(nullable = false , length = 280)
    private String content;
    
    private boolean active = true;

    @CreatedDate
    private LocalDateTime createdAt;
    
}
