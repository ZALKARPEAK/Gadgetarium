package com.example.gadegetarium.api;

import com.example.gadegetarium.dto.Basket.BasketBuyProductRequest;
import com.example.gadegetarium.dto.Basket.BasketResponse;
import com.example.gadegetarium.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketApi {

    private final BasketService basketService;

    @PostMapping("/buyProductUser")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public BasketResponse buyProductUser(@RequestBody BasketBuyProductRequest request) {
        return basketService.buyProductUser(request);
    }

    @DeleteMapping("/deleteProduct")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public BasketResponse deleteProduct(@RequestBody BasketBuyProductRequest request) {
        return basketService.deleteProduct(request);
    }

    @DeleteMapping("/clearBasket/{userId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public BasketResponse clearBasket(@PathVariable Long userId){
        return basketService.clearBasket(userId);
    }
}
