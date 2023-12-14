package com.example.gadegetarium.service.impl;

import com.example.gadegetarium.Exception.NotFoundException;
import com.example.gadegetarium.dto.Product.PaginationResponse;
import com.example.gadegetarium.dto.Product.ProductRequest;
import com.example.gadegetarium.dto.Product.ProductResponse;
import com.example.gadegetarium.entity.Brand;
import com.example.gadegetarium.entity.Comment;
import com.example.gadegetarium.entity.Product;
import com.example.gadegetarium.enums.Category;
import com.example.gadegetarium.repo.BrandRepo;
import com.example.gadegetarium.repo.ProductRepo;
import com.example.gadegetarium.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final BrandRepo brandRepo;

    @Override
    public ProductResponse getProductById(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() ->
                new NotFoundException("Product not found"));
        List<Long> commentIds = product.getComments()
                .stream()
                .map(Comment::getId)
                .collect(Collectors.toList());

        return ProductResponse.builder()
                .id(productId)
                .name(product.getName())
                .price(product.getPrice())
                .images(product.getImages())
                .characteristic(product.getCharacteristic())
                .isFavorite(product.isFavorite())
                .madeIn(product.getMadeIn())
                .category(product.getCategory())
                .brandId(product.getBrand().getId())
                .basketId(product.getBasket().getId())
                .commentIds(commentIds)
                .build();
    }
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product1 = productRepo.findProductByName(productRequest.getName())
                .orElseGet(() -> {
                    Product newProduct = new Product();
                    newProduct.setName(productRequest.getName());
                    newProduct.setPrice(productRequest.getPrice());
                    newProduct.setImages(productRequest.getImages());
                    newProduct.setCharacteristic(productRequest.getCharacteristic());
                    newProduct.setMadeIn(productRequest.getMadeIn());
                    newProduct.setCategory(productRequest.getCategory());
                    Brand brand = brandRepo.findBrandByBrandName(productRequest.getBrandName()).orElseThrow(() ->
                            new NotFoundException("Brand not found")
                    );

                    newProduct.setBrand(brand);

                    return productRepo.save(newProduct);
                });

        return ProductResponse.builder()
                .name(product1.getName())
                .price(product1.getPrice())
                .images(product1.getImages())
                .characteristic(product1.getCharacteristic())
                .isFavorite(product1.isFavorite())
                .madeIn(product1.getMadeIn())
                .category(product1.getCategory())
                .brandId(product1.getBrand().getId())
                .basketId(product1.getBasket().getId())
                .build();
    }
    @Override
    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        Product product = productRepo.findById(productId).orElseThrow(() ->
                new NotFoundException("Product not found"));

        product.setId(productId);
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setImages(productRequest.getImages());
        product.setCharacteristic(productRequest.getCharacteristic());
        product.setMadeIn(productRequest.getMadeIn());
        product.setCategory(productRequest.getCategory());

        Brand brand = brandRepo.findBrandByBrandName(productRequest.getBrandName()).orElseThrow(() ->
                new NotFoundException("Brand not found"));

        product.setBrand(brand);

        return ProductResponse.builder()
                .id(productId)
                .name(product.getName())
                .price(product.getPrice())
                .images(product.getImages())
                .characteristic(product.getCharacteristic())
                .isFavorite(product.isFavorite())
                .madeIn(product.getMadeIn())
                .category(product.getCategory())
                .brandId(product.getBrand().getId())
                .basketId(product.getBasket().getId())
                .build();
    }
    @Override
    public void deleteProduct(Long productId) {
    }
    @Override
    public PaginationResponse getAllProducts(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<ProductResponse> productResponses = productRepo.getAll(pageable);
        return PaginationResponse.builder()
                .productResponseList(productResponses.getContent())
                .currentPage(productResponses.getNumber() + 1)
                .pageSize(productResponses.getTotalPages())
                .build();
    }

    @Override
    public List<ProductResponse> getProductsByCategory(Category category) {
        return null;
    }

    @Override
    public List<ProductResponse> getFavoriteProducts(String userEmail) {
        return null;
    }

    @Override
    public List<ProductResponse> getProductsInBasket(String userEmail) {
        return null;
    }
}
