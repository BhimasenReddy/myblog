package com.myblog.controller;

import com.myblog.payload.PostDto;
import com.myblog.payload.PostResponse;
import com.myblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // http://localhost:8080/api/post
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> savePost(@Valid @RequestBody PostDto postDto, BindingResult result){
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.savePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED); //201(for save the record)
    }

    // http://localhost:8080/api/post/2
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<>("Post Is Successfully Deleted!!!", HttpStatus.OK);
    }

    // http://localhost:8080/api/post/1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto){
        PostDto dto1 = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dto1, HttpStatus.OK);
    }

    // http://localhost:8080/api/post
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getUpdatePost(@PathVariable("id") long id){
        PostDto upost = postService.getUpdatePost(id);
        return new ResponseEntity<>(upost, HttpStatus.OK);
    }

    // http://localhost:8080/api/post?pageNo=0&pageSize=5&sortBy=title&sortDir=desc
    @GetMapping
    public PostResponse getPost(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "id",required = false) String sortDir

    ){
        PostResponse postResponse = postService.getPost(pageNo,pageSize,sortBy,sortDir);
        return postResponse;
    }
}
