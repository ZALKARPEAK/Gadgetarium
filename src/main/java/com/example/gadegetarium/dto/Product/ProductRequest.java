package com.example.gadegetarium.dto.Product;

import com.example.gadegetarium.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ProductRequest {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    private int price;
    private List<String> images;
    private String characteristic;
    private String madeIn;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String brandName;
}
