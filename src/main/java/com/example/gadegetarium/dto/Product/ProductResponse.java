package com.example.gadegetarium.dto.Product;

import com.example.gadegetarium.enums.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private int price;
    private List<String> images;
    private String characteristic;
    private boolean isFavorite;
    private String madeIn;
    private Category category;
    private Long brandId;
    private Long basketId;
    private List<Long> commentIds;
    private List<Long> favoriteIds;

    public ProductResponse(String name, int price, List<String> images, String characteristic, boolean isFavorite, String madeIn, Category category) {
        this.name = name;
        this.price = price;
        this.images = images;
        this.characteristic = characteristic;
        this.isFavorite = isFavorite;
        this.madeIn = madeIn;
        this.category = category;
    }

    public ProductResponse(String name, int price, List<String> images, String characteristic, Category category) {
    }
}
