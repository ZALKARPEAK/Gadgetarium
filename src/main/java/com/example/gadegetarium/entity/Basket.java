package com.example.gadegetarium.entity;

import com.example.gadegetarium.entity.adducation.Id;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Basket extends Id {
    @ManyToMany(cascade = CascadeType.REMOVE)
    private List<Product> product;
    @OneToOne
    private User user;


}
