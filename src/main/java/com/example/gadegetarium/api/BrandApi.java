package com.example.gadegetarium.api;

import com.example.gadegetarium.dto.Brand.BrandRequest;
import com.example.gadegetarium.dto.Product.ProductResponseUser;
import com.example.gadegetarium.dto.SimpleResponse;
import com.example.gadegetarium.service.BrandService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandApi {
    private final BrandService brandService;

    @PostMapping("/saveBrand")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SimpleResponse saveBrand(@RequestBody @Valid BrandRequest brandRequest) {
        brandService.addBrand(brandRequest);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Saved")
                .build();
    }

    @PostMapping("/getProductFromBrand")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public List<ProductResponseUser> getProductFromBrand(@RequestParam String brandName){
        return brandService.getProductsByBrand(brandName);
    }
}
