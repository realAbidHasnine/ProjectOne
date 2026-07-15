package com.Rush.ProjectOne.controller;

import com.Rush.ProjectOne.dto.PostRequestDTO;
import com.Rush.ProjectOne.dto.PostResponseDTO;
import com.Rush.ProjectOne.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponseDTO> createPost(@Valid @RequestBody PostRequestDTO request) {
        PostResponseDTO response = postService.createPost(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<PostResponseDTO> response = postService.getAllPosts();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
