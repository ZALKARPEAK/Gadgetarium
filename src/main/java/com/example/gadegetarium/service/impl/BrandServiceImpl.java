package com.example.gadegetarium.service.impl;

import com.example.gadegetarium.Exception.AlreadyExistsException;
import com.example.gadegetarium.Exception.NotFoundException;
import com.example.gadegetarium.dto.Brand.BrandRequest;
import com.example.gadegetarium.dto.Brand.BrandResponse;
import com.example.gadegetarium.dto.Product.ProductResponse;
import com.example.gadegetarium.entity.Brand;
import com.example.gadegetarium.repo.BrandRepo;
import com.example.gadegetarium.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepo brandRepo;


    @Override
    public BrandResponse addBrand(BrandRequest request) {
        if (brandRepo.existsByBrandName(request.getBrandName())) {
            throw new AlreadyExistsException("Brand already exists");
        }

        Brand brand = new Brand();
        brand.setBrandName(request.getBrandName());
        brand.setImage(request.getImage());

        brandRepo.save(brand);

        return BrandResponse.builder().brandName(brand.getBrandName())
                .image(brand.getImage()).build();
    }

    @Override
    public List<ProductResponse> getProductsByBrand(String brandName) {
        Brand brand = brandRepo.findBrandByBrandName(brandName).orElseThrow(() ->
                new NotFoundException("Brand not found"));

        return brand.getProducts().stream().map(product -> new ProductResponse(
                product.getName(),
                product.getPrice(),
                product.getImages(),
                product.getCharacteristic(),
                product.getCategory())
        ).toList();
    }
}
