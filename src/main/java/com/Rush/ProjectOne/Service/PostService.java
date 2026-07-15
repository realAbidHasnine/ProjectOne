package com.Rush.ProjectOne.service;

import com.Rush.ProjectOne.dto.PostRequestDTO;
import com.Rush.ProjectOne.dto.PostResponseDTO;
import com.Rush.ProjectOne.entity.PostEntity;
import com.Rush.ProjectOne.entity.UserEntity;
import com.Rush.ProjectOne.exception.InvalidOperationException;
import com.Rush.ProjectOne.exception.ResourceNotFoundException;
import com.Rush.ProjectOne.repository.PostRepository;
import com.Rush.ProjectOne.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public PostResponseDTO createPost(PostRequestDTO request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidOperationException(
                        "No user registered with email: " + request.getEmail()));

        PostEntity post = PostEntity.builder()
                .authorName(request.getAuthorName())
                .content(request.getContent())
                .user(user)
                .build();

        PostEntity saved = postRepository.save(post);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PostResponseDTO> getAllPosts() {
        return postRepository.findByActiveTrue()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void deletePost(Long id) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Post", id));

        if (!post.isActive()) {
            throw new InvalidOperationException("Post is already deleted");
        }

        post.setActive(false);
        postRepository.save(post);
    }

    private PostResponseDTO toResponse(PostEntity post) {
        return PostResponseDTO.builder()
                .id(post.getId())
                .authorName(post.getAuthorName())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .userId(post.getUser().getId())
                .userEmail(post.getUser().getEmail())
                .build();
    }
}
