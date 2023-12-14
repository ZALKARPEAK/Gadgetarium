package com.example.gadegetarium.entity;

import com.example.gadegetarium.entity.adducation.Id;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.ZonedDateTime;

@Data
@Entity
public class Comment extends Id{
    private String comment;
    private ZonedDateTime createDate;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    private Product product;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private User user;
}
