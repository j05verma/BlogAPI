package com.blogapi.service.impl;

import com.blogapi.entity.Post;
import com.blogapi.exception.ResourceNotFoundException;
import com.blogapi.payload.PostDto;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {
        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

       Post post= mapToEntity(postDto);

        Post savePost = postRepo.save(post);

        PostDto dto = mapToDto(savePost);
        return dto;
    }

    @Override
    public PostDto getPostById(long id) {
      Post post= postRepo.findById(id).orElseThrow(

              ()-> new ResourceNotFoundException(id)
      );

        PostDto dto=mapToDto(post);
        return dto;
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {

        //ternary operator
       // sortDir.equalsIgnoreCase("asc")?value1:value2
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> posts = postRepo.findAll(pageable);

        List<Post> content = posts.getContent();

        List<PostDto> postDtos = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public void deletePost(long id) {
      Post post=  postRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(id)
        );
      postRepo.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        //first check post there or not
        Post post=  postRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(id)
        );

        Post updateContent = mapToEntity(postDto);
        updateContent.setId(post.getId());

        Post updatePostInfo = postRepo.save(updateContent);

        return mapToDto(updatePostInfo);
    }

    //convert entity to dto
    PostDto mapToDto(Post post)
    {
       // PostDto dto=new PostDto();
       PostDto dto= modelMapper.map(post,PostDto.class);
      //  dto.setId(post.getId());
      //  dto.setTitle(post.getTitle());
       // dto.setDescription(post.getDescription());
     //   dto.setContent(post.getContent());
        return  dto;
    }
    //convert dto to entity
    Post mapToEntity(PostDto postDto)
    {
        Post post = modelMapper.map(postDto, Post.class);
        //  Post post=new Post();
     //   post.setTitle(postDto.getTitle());
      //  post.setDescription(postDto.getDescription());
      //  post.setContent(postDto.getContent());
        return post;
    }
}
