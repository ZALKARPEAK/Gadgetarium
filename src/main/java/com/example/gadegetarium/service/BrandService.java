package com.example.gadegetarium.service;

import com.example.gadegetarium.dto.Brand.BrandRequest;
import com.example.gadegetarium.dto.Brand.BrandResponse;
import com.example.gadegetarium.dto.Product.ProductResponseUser;

import java.util.List;

public interface BrandService {
    BrandResponse addBrand(BrandRequest request);
    List<ProductResponseUser> getProductsByBrand(String brandName);
}
