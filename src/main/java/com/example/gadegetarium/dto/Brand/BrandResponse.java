package com.example.gadegetarium.dto.Brand;

import com.example.gadegetarium.dto.Product.ProductResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class BrandResponse {
    private String brandName;
    private String image;
    private List<ProductResponse> products;


}
