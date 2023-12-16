package com.example.gadegetarium.service;

import com.example.gadegetarium.dto.Basket.BasketBuyProductRequest;
import com.example.gadegetarium.dto.Basket.BasketRequest;
import com.example.gadegetarium.dto.Basket.BasketResponse;

public interface BasketService {
    BasketResponse buyProductUser(BasketBuyProductRequest request);
    BasketResponse deleteProduct(BasketBuyProductRequest request);
    BasketResponse clearBasket(Long userId);

}
