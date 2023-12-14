package com.example.gadegetarium.service;

import com.example.gadegetarium.dto.Brand.BrandRequest;
import com.example.gadegetarium.dto.Brand.BrandResponse;
import com.example.gadegetarium.dto.Product.ProductResponse;

import java.util.List;

public interface BrandService {
    BrandResponse addBrand(BrandRequest request);
    List<ProductResponse> getProductsByBrand(String brandName);
}
