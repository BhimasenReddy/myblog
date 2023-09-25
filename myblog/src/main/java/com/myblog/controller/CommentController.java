package com.myblog.controller;

import com.myblog.entity.Comment;
import com.myblog.payload.CommentDto;
import com.myblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PostRemove;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    // http://localhost:8080/api/posts/1/comments
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> saveComment(@PathVariable("postId") long postId, @RequestBody CommentDto commentDto){
        CommentDto comdto = commentService.saveComment(postId, commentDto);
        return new ResponseEntity<>(comdto, HttpStatus.CREATED);
    }

    // http://localhost:8080/api/posts/1/comments
    @DeleteMapping("/posts/{postId}/comments")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId){
        commentService.deleteComment(postId);
        return new ResponseEntity<>("Post delete Successfully with Id: "+postId, HttpStatus.OK);
    }

    // http://localhost:8080/api/posts/3/comments
    @PutMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId, @RequestBody CommentDto commentDto){
        CommentDto getDto = commentService.updateComment(postId,commentDto);
        return new ResponseEntity<>(getDto,HttpStatus.OK);
    }

//    // http://localhost:8080/api/posts/5/comments
//    @GetMapping("/posts/{postId}/comments")
//    public ResponseEntity<CommentDto> getComments(@PathVariable("postId") long postId){
//        CommentDto comDto = commentService.getComments(postId);
//        return new ResponseEntity<>(comDto,HttpStatus.OK);
//    }

    // http://localhost:8080/api/posts/1/comments
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId){
        return commentService.getCommentsByPostId(postId);
    }

    // http://localhost:8080/api/posts/1/comments/1
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public CommentDto getCommentId(@PathVariable("postId") long postId,@PathVariable("commentId")long commentId){
        return commentService.getCommentId(postId,commentId);
    }

    // http://localhost:8080/api/comments
    @GetMapping("/comments")
    public List<CommentDto> getAllCommentsById(){
       return commentService.getAllCommentsById();
    }

    // http://localhost:8080/api/posts/1/comments/3
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteCommentsById(@PathVariable("postId")long postId,@PathVariable("commentId")long commentId){
        commentService.deleteCommentsById(postId,commentId);
        return new ResponseEntity<>("Comment is deleted with id: "+commentId, HttpStatus.OK);
    }
}
