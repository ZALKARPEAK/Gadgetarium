package com.example.gadegetarium.service.impl;

import com.example.gadegetarium.Exception.NotFoundException;
import com.example.gadegetarium.dto.Product.PaginationResponse;
import com.example.gadegetarium.dto.Product.ProductRequest;
import com.example.gadegetarium.dto.Product.ProductResponse;
import com.example.gadegetarium.entity.*;
import com.example.gadegetarium.enums.Category;
import com.example.gadegetarium.repo.BasketRepo;
import com.example.gadegetarium.repo.BrandRepo;
import com.example.gadegetarium.repo.ProductRepo;
import com.example.gadegetarium.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                .characteristic(product.getCharacteristic())
                .isFavorite(product.isFavorite())
                .madeIn(product.getMadeIn())
                .category(product.getCategory())
                .brandId(product.getBrand().getId())
                .commentIds(commentIds)
                .build();
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Product newProduct = new Product();
        newProduct.setName(productRequest.getName());
        newProduct.setPrice(productRequest.getPrice());
        newProduct.setImages(productRequest.getImages());
        newProduct.setCharacteristic(productRequest.getCharacteristic());
        newProduct.setMadeIn(productRequest.getMadeIn());
        newProduct.setCategory(productRequest.getCategory());
        Brand brand = brandRepo.findBrandByBrandName(productRequest.getBrandName())
                .orElseThrow(() -> new NotFoundException("Brand not found"));

        if (newProduct.getBasket() == null) {
            newProduct.setBasket(new ArrayList<>());
        }

        newProduct.setBrand(brand);

        Product savedProduct = productRepo.save(newProduct);

        return ProductResponse.builder()
                .name(savedProduct.getName())
                .price(savedProduct.getPrice())
                .characteristic(savedProduct.getCharacteristic())
                .isFavorite(savedProduct.isFavorite())
                .madeIn(savedProduct.getMadeIn())
                .category(savedProduct.getCategory())
                .brandId(savedProduct.getBrand().getId())
                .build();
    }

    @Override
    public void deleteProduct(Long productId) {
        Product product = productRepo.findById(productId).orElseThrow(() ->
                new NotFoundException("Product not found"));

        product.setCategory(null);
        product.setComments(null);
        product.setImages(null);
        product.setBasket(null);
        productRepo.delete(product);
    }

    @Override
    public PaginationResponse getAllProducts(int page, int size) {
        if (page <= 0 || size <= 0) {
            throw new IllegalStateException("Page and size must be greater than zero");
        }

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
        List<Product> products = productRepo.findByCategory(category);
        return convertToProductResponseList(products);
    }

    @Override
    public List<ProductResponse> getFavoriteProducts(String userEmail) {
        List<Product> favoriteProducts = productRepo.findFavoriteProductsByUserEmail(userEmail);
        return convertToProductResponseList(favoriteProducts);
    }

    @Override
    public List<ProductResponse> getProductByCategoryAndPrice(String ascOrDesc, Category category) {
        List<ProductResponse> allProducts;
        if (ascOrDesc.equalsIgnoreCase("desc")) {
            allProducts = productRepo.getProductByCategoryAndPriceDesc(category);
        } else if (ascOrDesc.equalsIgnoreCase("asc")) {
            allProducts = productRepo.getProductByCategoryAndPriceAsc(category);
        } else {
            return null;
        }

       return convertToProductResponse(allProducts);
    }

    private List<ProductResponse> convertToProductResponseList(List<Product> products) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            productResponses.add(ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .characteristic(product.getCharacteristic())
                    .isFavorite(product.isFavorite())
                    .madeIn(product.getMadeIn())
                    .category(product.getCategory())
                    .brandId(product.getBrand() != null ? product.getBrand().getId() : null)
                    .basketId(product.getBasket() != null ? product.getBasket().stream().findFirst()
                            .orElse(null).getId(): null)
                    .commentIds(getCommentIds(product.getComments()))
                    .favoriteIds(getFavoriteIds(product.getFavorite()))
                    .build());
        }
        return productResponses;
    }

    private List<ProductResponse> convertToProductResponse(List<ProductResponse> products) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (ProductResponse product : products) {
            productResponses.add(ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .price(product.getPrice())
                    .characteristic(product.getCharacteristic())
                    .isFavorite(product.isFavorite())
                    .madeIn(product.getMadeIn())
                    .category(product.getCategory())
                    .brandId(product.getBrandId() != null ? product.getBrandId() : null)
                    .basketId(product.getBasketId() != null ? product.getBasketId() : null)
                    .build());
        }
        return productResponses;
    }

    private List<Long> getCommentIds(List<Comment> comments) {
        return comments.stream()
                .map(Comment::getId)
                .collect(Collectors.toList());
    }

    private List<Long> getFavoriteIds(List<Favorite> favorites) {
        return favorites.stream()
                .map(Favorite::getId)
                .collect(Collectors.toList());
    }
}