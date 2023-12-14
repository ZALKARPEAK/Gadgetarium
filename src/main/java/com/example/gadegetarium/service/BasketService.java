package com.example.gadegetarium.service;

import com.example.gadegetarium.dto.Basket.BasketRequest;
import com.example.gadegetarium.dto.Basket.BasketResponse;

public interface BasketService {
    BasketResponse buyProductUser(BasketRequest request);
    BasketResponse assignProductInBasket(BasketRequest request);
    BasketResponse deleteProduct(BasketRequest request);
    BasketResponse clearBasket(BasketRequest request);

}
