package com.myblog.service.impl;

import com.myblog.entity.Comment;
import com.myblog.entity.Post;
import com.myblog.exception.ResourceNotFound;
import com.myblog.payload.CommentDto;
import com.myblog.repository.CommentRepository;
import com.myblog.repository.PostRepository;
import com.myblog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepository postRepository;
    private ModelMapper mapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.mapper = mapper;
    }

    @Override
    public CommentDto saveComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post not found with id: " + postId)
        );
        comment.setPost(post);
        Comment saveComment = commentRepository.save(comment);
        CommentDto dtos = mapToDto(saveComment);
        return dtos;
    }

    @Override
    public void deleteComment(long postId) {
        commentRepository.deleteById(postId);
    }

    @Override
    public CommentDto updateComment(long postId, CommentDto commentDto) {
        Comment comments = commentRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("PostId does not Exist with Id: " + postId)
        );
        comments.setName(commentDto.getName());
        comments.setEmail(commentDto.getEmail());
        comments.setBody(commentDto.getBody());
        Comment allComments = commentRepository.save(comments);
        CommentDto alldtos = mapToDto(allComments);
        return alldtos;
    }

    @Override
    public CommentDto getComments(long postId) {
        Comment comments = commentRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("PostId does not Exist with Id: "+postId)
        );
        CommentDto newDtos = mapToDto(comments);
        return newDtos;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
      Post post =  postRepository.findById(postId).orElseThrow(
               ()->new ResourceNotFound("Post is not found with postId: "+postId)
       );
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> commentDtos = comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public CommentDto getCommentId(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("Post is not found with id: "+postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFound("Comment is not found with id: "+commentId)
        );
        CommentDto commentDto = mapToDto(comment);
        return commentDto;
    }

    @Override
    public List<CommentDto> getAllCommentsById() {
        List<Comment> all = commentRepository.findAll();
        List<CommentDto> alldtos = all.stream().map(x -> mapToDto(x)).collect(Collectors.toList());
        return alldtos;
    }

    @Override
    public void deleteCommentsById(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFound("Post is not found with id: "+postId)
        );
        Comment comment  = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFound("Comment is not found with id: "+commentId)
        );
        commentRepository.deleteById(commentId);
    }

    Comment mapToEntity(CommentDto commentDto){
        Comment comment = mapper.map(commentDto,Comment.class);
        return comment;
    }

    CommentDto mapToDto(Comment comment){
        CommentDto commentDto = mapper.map(comment, CommentDto.class);
        return commentDto;
    }
}
