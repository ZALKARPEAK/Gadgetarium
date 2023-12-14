package com.example.gadegetarium.dto.Product;

import com.example.gadegetarium.enums.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class ProductResponseUser {
    private String name;
    private int price;
    private List<String> images;
    private String characteristic;
    private Category category;

    public ProductResponseUser(String name, int price, List<String> images, String characteristic, Category category) {
        this.name = name;
        this.price = price;
        this.images = images;
        this.characteristic = characteristic;
        this.category = category;
    }
}
