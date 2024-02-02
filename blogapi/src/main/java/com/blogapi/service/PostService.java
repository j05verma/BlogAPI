package com.blogapi.service;

import com.blogapi.payload.PostDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PostService {

    public PostDto createPost(PostDto postDto);

    PostDto getPostById(long id);

    List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    void deletePost(long id);

    // i want that updated information in responseSession on postman-> PostDto type not void
    PostDto updatePost(long id, PostDto postDto);
}
