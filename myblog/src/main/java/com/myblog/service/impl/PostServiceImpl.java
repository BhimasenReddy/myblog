package com.myblog.service.impl;

import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.repository.PostRepository;
import com.myblog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper) {

        this.postRepo = postRepo;
        this.modelMapper= modelMapper;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post savedPost = postRepo.save(post);
        PostDto pdto = mapToDto(savedPost);
        return pdto;
    }

    @Override
    public void deletePost(long id) {
        postRepo.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {           // Dto to Entity
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post Not Found with id=" + id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post save = postRepo.save(post);
        PostDto dto = mapToDto(save);       // Entity to Dto
        return dto;
    }

    @Override
    public PostDto getUpdatePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFound("Post Is Not Found With Id=" + id)
        );
        PostDto postDto = mapToDto(post);
        return postDto;
    }

    @Override
    public PostResponse getPost(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending():
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize, sort);
        Page<Post> pageposts = postRepo.findAll(pageable);
        List<Post> posts = pageposts.getContent();
        List<PostDto> dto = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDto(dto);
        postResponse.setPageNo(pageposts.getNumber());
        postResponse.setPageSize(pageposts.getSize());
        postResponse.setTotalElement(pageposts.getTotalElements());
        postResponse.setTotalPages(pageposts.getTotalPages());
        postResponse.setLast(pageposts.isLast());

        return postResponse;
    }

    Post mapToEntity(PostDto postDto){
        Post post = modelMapper.map(postDto, Post.class);
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    PostDto mapToDto(Post post){
        PostDto dto=modelMapper.map(post,PostDto.class);
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;
    }
}
