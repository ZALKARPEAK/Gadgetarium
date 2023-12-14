package com.example.gadegetarium.api;

import com.example.gadegetarium.dto.Product.PaginationResponse;
import com.example.gadegetarium.dto.Product.ProductRequest;
import com.example.gadegetarium.dto.Product.ProductResponse;
import com.example.gadegetarium.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductService productService;

    @GetMapping("/All")
    public PaginationResponse getAllProduct(int page, int size){
        return productService.getAllProducts(page, size);
    }

    @PostMapping("/Add")
    public ProductResponse saveProduct(@RequestBody ProductRequest productRequest){
        return productService.createProduct(productRequest);
    }
}
