package com.example.gadegetarium.api;

import com.example.gadegetarium.dto.Comment.CommentRequest;
import com.example.gadegetarium.dto.SimpleResponse;
import com.example.gadegetarium.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentApi {

    private final CommentService commentService;

    @PostMapping("/comment/{productId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<SimpleResponse> saveComment(@PathVariable Long productId , @RequestBody CommentRequest commentRequest){
        return ResponseEntity.ok( commentService.commentToProduct(productId,commentRequest));
    }

    @DeleteMapping("/delete/{productId}/{commentId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<SimpleResponse> deleteComment(@PathVariable Long productId, @PathVariable Long commentId){
        return ResponseEntity.ok( commentService.deleteComment(productId,commentId));
    }
}

