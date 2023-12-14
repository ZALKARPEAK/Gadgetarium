package com.example.gadegetarium.entity;

import com.example.gadegetarium.entity.adducation.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Favorite extends Id {
    @ManyToOne
    private User user;
    @ManyToOne
    private Product product;
}
