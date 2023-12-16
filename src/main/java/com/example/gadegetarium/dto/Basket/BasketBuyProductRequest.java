package com.example.gadegetarium.dto.Basket;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class BasketBuyProductRequest {
    private Long productId;
    private String emailUser;
}
