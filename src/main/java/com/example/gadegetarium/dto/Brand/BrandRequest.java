package com.example.gadegetarium.dto.Brand;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BrandRequest {
    private String brandName;
    private String image;

}
