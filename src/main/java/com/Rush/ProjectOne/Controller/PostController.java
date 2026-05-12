package com.Rush.ProjectOne.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.Rush.ProjectOne.DTO.PostRequestDTO;
import com.Rush.ProjectOne.DTO.PostResponseDTO;
import com.Rush.ProjectOne.Service.PostService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/posts")
public class PostController{

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @PostMapping
    public PostResponseDTO createPOST(@Valid @RequestBody PostRequestDTO postDTO){
        return postService.createPost(postDTO);
    }

    @GetMapping
    public List<PostResponseDTO> getAllPOSTS(){
        return postService.getAllPosts();
    }

    @DeleteMapping("/{id}")
    public String deletePOST(@PathVariable Long id){
        postService.deletePost(id);
        return "User Deleted Successfully";
    }
}