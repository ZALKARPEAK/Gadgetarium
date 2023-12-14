package com.example.gadegetarium.service.impl;

import com.example.gadegetarium.dto.Basket.BasketRequest;
import com.example.gadegetarium.dto.Basket.BasketResponse;
import com.example.gadegetarium.repo.BasketRepo;
import com.example.gadegetarium.repo.ProductRepo;
import com.example.gadegetarium.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {
    /*User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();*/

    private final BasketRepo basketRepo;
    private final ProductRepo productRepo;

    @Override
    public BasketResponse buyProductUser(BasketRequest request) {

        return null;
    }

    @Override
    public BasketResponse assignProductInBasket(BasketRequest request) {
        return null;
    }

    @Override
    public BasketResponse deleteProduct(BasketRequest request) {
        return null;
    }

    @Override
    public BasketResponse clearBasket(BasketRequest request) {
        return null;
    }
}
