package com.example.gadegetarium.service.impl;

import com.example.gadegetarium.Exception.NotFoundException;
import com.example.gadegetarium.dto.Basket.BasketBuyProductRequest;
import com.example.gadegetarium.dto.Basket.BasketResponse;
import com.example.gadegetarium.entity.Basket;
import com.example.gadegetarium.entity.Product;
import com.example.gadegetarium.entity.User;
import com.example.gadegetarium.repo.ProductRepo;
import com.example.gadegetarium.repo.UserRepo;
import com.example.gadegetarium.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    @Override
    public BasketResponse buyProductUser(BasketBuyProductRequest request) {
        User user = userRepo.findUserByEmail(request.getEmailUser())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Product product = productRepo.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundException("Product not found"));

        Basket basket = user.getBasket();

        if (basket == null) {
            basket = new Basket();
            basket.setUser(user);
            user.setBasket(basket);
        }

        List<Product> productList = basket.getProduct();

        if (productList == null) {
            productList = new ArrayList<>();
            basket.setProduct(productList);
        }

        productList.add(product);
        product.setBasket(Collections.singletonList(basket));

        userRepo.save(user);

        return BasketResponse.builder().message("Buy successfully").build();
    }

    @Override
    public BasketResponse deleteProduct(BasketBuyProductRequest request) {
        User user = userRepo.findUserByEmail(request.getEmailUser()).orElseThrow(() ->
                new NotFoundException("User not found"));
        Product product = productRepo.findById(request.getProductId()).orElseThrow(() ->
                new NotFoundException("Product not found"));

        for (Product p : user.getBasket().getProduct()) {
            if (p.equals(product)) {
                user.getBasket().getProduct().remove(p);
                for (Basket b : product.getBasket()) {
                    if (b.getUser().equals(user)) {
                        b.getProduct().remove(product);
                    }
                }
            }
        }
        return BasketResponse.builder().message("Delete successfully").build();
    }

    @Override
    public BasketResponse clearBasket(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));

        Basket basket = user.getBasket();

        if (basket != null) {
            List<Product> productList = basket.getProduct();

            if (productList != null && !productList.isEmpty()) {
                productList.clear();

                userRepo.save(user);

                return BasketResponse.builder().message("Basket cleared successfully").build();
            } else {
                return BasketResponse.builder().message("Basket is already empty").build();
            }
        } else {
            throw new NotFoundException("User has no basket");
        }
    }
}
