package com.example.gadegetarium.entity;

import com.example.gadegetarium.entity.adducation.Id;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Basket extends Id {
    @OneToMany(cascade = CascadeType.DETACH)
    private List<Product> product;
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    private User user;


}
