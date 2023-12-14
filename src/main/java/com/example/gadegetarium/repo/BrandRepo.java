package com.example.gadegetarium.repo;

import com.example.gadegetarium.dto.Brand.BrandRequest;
import com.example.gadegetarium.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BrandRepo extends JpaRepository<Brand, Long > {

    Optional<Brand> findBrandByBrandName(String brandName);
    boolean existsByBrandName(String brandName);
    List<Brand> getAllProductsByBrandName(BrandRequest brandRequest);
}
