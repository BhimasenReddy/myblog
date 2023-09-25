package com.myblog.service;

import com.myblog.payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto saveComment(long postId, CommentDto commentDto);

    void deleteComment(long postId);

    CommentDto updateComment(long postId, CommentDto commentDto);

    CommentDto getComments(long postId);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getCommentId(long postId, long commentId);

    List<CommentDto> getAllCommentsById();

    void deleteCommentsById(long postId, long commentId);
}
