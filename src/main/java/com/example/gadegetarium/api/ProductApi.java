package com.example.gadegetarium.api;

import com.example.gadegetarium.dto.Product.PaginationResponse;
import com.example.gadegetarium.dto.Product.ProductRequest;
import com.example.gadegetarium.dto.Product.ProductResponse;
import com.example.gadegetarium.enums.Category;
import com.example.gadegetarium.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductService productService;


    @GetMapping("/ById")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ProductResponse getProductById(@RequestParam Long productId) {
        return productService.getProductById(productId);
    }

    @GetMapping("/All")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public PaginationResponse getAllProduct(@RequestParam int page, @RequestParam int size) {
        return productService.getAllProducts(page, size);
    }

    @DeleteMapping("/Delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteProduct(@RequestParam Long productId) {
        productService.deleteProduct(productId);
    }

    @PostMapping("/Add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ProductResponse saveProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @GetMapping("/ByCategory")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<ProductResponse> getProductsByCategory(@RequestParam Category category) {
        return productService.getProductsByCategory(category);
    }

    @GetMapping("/Favorite")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<ProductResponse> getFavoriteProducts(@RequestParam String userEmail) {
        return productService.getFavoriteProducts(userEmail);
    }

    @GetMapping("/ByCategoryAndPrice")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<ProductResponse> getProductByCategoryAndPrice(
            @RequestParam String ascOrDesc, @RequestParam Category category) {
        return productService.getProductByCategoryAndPrice(ascOrDesc, category);
    }
}
