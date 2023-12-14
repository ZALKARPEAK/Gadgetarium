package com.example.gadegetarium.entity;

import com.example.gadegetarium.entity.adducation.Id;
import com.example.gadegetarium.enums.Category;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Product extends Id {
    private String name;
    private int price;
    @ElementCollection
    private List<String> images;
    private String characteristic;
    private boolean isFavorite;
    private String madeIn;
    private Category category;

    @ManyToOne
    private Brand brand;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST})
    private Basket basket;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product",cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Comment> comments;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Favorite> favorite;
}
