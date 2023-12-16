package com.example.gadegetarium.service;

import com.example.gadegetarium.dto.Comment.CommentRequest;
import com.example.gadegetarium.dto.SimpleResponse;

public interface CommentService {
    SimpleResponse commentToProduct(Long productId, CommentRequest commentRequest);
    SimpleResponse deleteComment(Long productId,Long commentId);
}
