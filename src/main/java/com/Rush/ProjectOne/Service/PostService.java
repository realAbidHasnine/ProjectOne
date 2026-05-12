package com.Rush.ProjectOne.Service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.Rush.ProjectOne.DTO.PostRequestDTO;
import com.Rush.ProjectOne.DTO.PostResponseDTO;
import com.Rush.ProjectOne.Entity.PostEntity;
import com.Rush.ProjectOne.Entity.UserEntity;
import com.Rush.ProjectOne.Repository.PostRepository;
import com.Rush.ProjectOne.Repository.UserRepository;

@Service
public class PostService {

    private final PostRepository postRepo;

    private final UserRepository userRepo;

    public PostService(PostRepository postRepo,UserRepository userRepo) {
        this.postRepo = postRepo;
        this.userRepo = userRepo;
    }

    private PostEntity toPost(PostRequestDTO postReqDTO) {

        PostEntity postEntity = new PostEntity();

        postEntity.setAuthorName(postReqDTO.getAuthorName());
        postEntity.setContent(postReqDTO.getContent());
        postEntity.setEmail(postReqDTO.getEmail());

        return postEntity;

    }

    private PostResponseDTO toPostResponse(PostEntity post) {

        PostResponseDTO postBody = new PostResponseDTO();

        postBody.setId(post.getId());
        postBody.setAuthorName(post.getAuthorName());
        postBody.setContent(post.getContent());
        postBody.setCreatedAt(post.getCreatedAt());

        return postBody;
    }

    public PostResponseDTO createPost(PostRequestDTO postReqDTO) {

        UserEntity poster =  userRepo.findByEmail(postReqDTO.getEmail()).orElse(null);

        if(poster == null){
            throw new RuntimeException("User Not Registered Please Register with Email");
        }

        PostEntity post = toPost(postReqDTO);
        PostEntity savPostEntity = postRepo.save(post);

        return toPostResponse(savPostEntity);
    }

    public List<PostResponseDTO> getAllPosts() {
        return postRepo.findByActiveTrue()
                .stream()
                .map(post -> toPostResponse(post))
                .toList();
    }

    public void deletePost(Long id) {
        PostEntity post = postRepo.findById(id).orElse(null);

        if (post == null) {
            throw new RuntimeException("User All ready deleted");
        }
        post.setActive(false);
        postRepo.save(post);
    }

}
