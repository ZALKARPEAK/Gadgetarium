package com.example.gadegetarium.service;

import com.example.gadegetarium.dto.Product.PaginationResponse;
import com.example.gadegetarium.dto.Product.ProductRequest;
import com.example.gadegetarium.dto.Product.ProductResponse;
import com.example.gadegetarium.enums.Category;

import java.util.List;

public interface ProductService {
    ProductResponse getProductById(Long productId);
    ProductResponse createProduct(ProductRequest productRequest);
    ProductResponse updateProduct(Long productId, ProductRequest productRequest);
    void deleteProduct(Long productId);
    PaginationResponse getAllProducts(int page, int size);
    List<ProductResponse> getProductsByCategory(Category category);
    List<ProductResponse> getFavoriteProducts(String userEmail);
    List<ProductResponse> getProductsInBasket(String userEmail);

}
