package com.example.gadegetarium.api;

import com.example.gadegetarium.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketApi {

    private BasketService basketService;


}
