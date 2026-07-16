package com.Rush.ProjectOne.service;

import com.Rush.ProjectOne.dto.CommentRequestDTO;
import com.Rush.ProjectOne.dto.CommentResponseDTO;
import com.Rush.ProjectOne.entity.CommentEntity;
import com.Rush.ProjectOne.entity.PostEntity;
import com.Rush.ProjectOne.entity.UserEntity;
import com.Rush.ProjectOne.exception.InvalidOperationException;
import com.Rush.ProjectOne.exception.ResourceNotFoundException;
import com.Rush.ProjectOne.repository.CommentRepository;
import com.Rush.ProjectOne.repository.PostRepository;
import com.Rush.ProjectOne.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CommentResponseDTO createComment(CommentRequestDTO request) {
        UserEntity user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidOperationException(
                        "No user registered with email: " + request.getEmail()));

        PostEntity post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", request.getPostId()));

        if (!post.isActive()) {
            throw new InvalidOperationException("Cannot comment on a deleted post");
        }

        CommentEntity comment = CommentEntity.builder()
                .content(request.getContent())
                .post(post)
                .user(user)
                .build();

        CommentEntity saved = commentRepository.save(comment);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getCommentsByPost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResourceNotFoundException("Post", postId);
        }

        return commentRepository.findByPostIdAndActiveTrue(postId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public void deleteComment(Long id) {
        CommentEntity comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", id));

        if (!comment.isActive()) {
            throw new InvalidOperationException("Comment is already deleted");
        }

        comment.setActive(false);
        commentRepository.save(comment);
    }

    private CommentResponseDTO toResponse(CommentEntity comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .postId(comment.getPost().getId())
                .authorName(comment.getUser().getFullName())
                .userEmail(comment.getUser().getEmail())
                .build();
    }
}
