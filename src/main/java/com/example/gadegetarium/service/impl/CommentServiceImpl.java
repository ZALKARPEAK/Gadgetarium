package com.example.gadegetarium.service.impl;

import com.example.gadegetarium.Exception.AccessDenied;
import com.example.gadegetarium.Exception.NotFoundException;
import com.example.gadegetarium.dto.Comment.CommentRequest;
import com.example.gadegetarium.dto.SimpleResponse;
import com.example.gadegetarium.entity.Comment;
import com.example.gadegetarium.entity.Product;
import com.example.gadegetarium.entity.User;
import com.example.gadegetarium.repo.CommentRepo;
import com.example.gadegetarium.repo.ProductRepo;
import com.example.gadegetarium.repo.UserRepo;
import com.example.gadegetarium.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final UserRepo userRepository;
    private final CommentRepo commentRepository;
    private final ProductRepo productRepository;

    @Override
    public SimpleResponse commentToProduct(Long productId, CommentRequest commentRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDenied("Authentication required to delete a comment !!!");
        }
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(()-> new NotFoundException("User with id:"+email+" not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(()-> new NotFoundException("Product with id:"+productId+" not found"));

        Comment comment1 = commentRequest.build();
        user.addComment(comment1);
        product.getComments().add(comment1);
        comment1.setProduct(product);
        commentRepository.save(comment1);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User is commented...")
                .build();
    }

    @Override
    public SimpleResponse deleteComment(Long productId,Long commentId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDenied("Authentication required to delete a comment !!!");
        }
        String email = authentication.getName();
        User user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new NotFoundException("User with id:" + email + " not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product with id:" + productId + " not found"));

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Comment with id:" + commentId + " not found"));

        if (!comment.getUser().equals(user)) {
            throw new NotFoundException("You have permission to delete this comment");
        }
        product.getComments().remove(comment);
        user.getComments().remove(comment);
        commentRepository.delete(comment);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Comment is deleted...")
                .build();

    }
}
