package com.myblog.service;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;

import java.util.List;

public interface PostService {

    PostDto savePost(PostDto postDto);

    void deletePost(long id);


    PostDto updatePost(long id, PostDto postDto);

    PostDto getUpdatePost(long id);

    PostResponse getPost(int pageNo, int pageSize, String sortBy, String sortDir);
}
