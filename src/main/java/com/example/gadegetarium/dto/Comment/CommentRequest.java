package com.example.gadegetarium.dto.Comment;

import com.example.gadegetarium.entity.Comment;
import lombok.Builder;

@Builder
public record CommentRequest(String comment){

    public Comment build() {
        Comment comment = new Comment();
        comment.setComment(this.comment);
        return comment;
    }
}