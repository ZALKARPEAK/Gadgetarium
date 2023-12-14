package com.example.gadegetarium.repo;

import com.example.gadegetarium.dto.Product.PaginationResponse;
import com.example.gadegetarium.dto.Product.ProductResponse;
import com.example.gadegetarium.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findProductByName(String productName);
    @Query("SELECT NEW com.example.gadegetarium.dto.Product.ProductResponse(p.name, p.price, p.images, p.characteristic, p.isFavorite, p.madeIn, p.category) FROM Product p")
    Page<ProductResponse> getAll(Pageable pageable);
}
