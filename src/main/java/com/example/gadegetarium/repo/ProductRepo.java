package com.example.gadegetarium.repo;

import com.example.gadegetarium.dto.Product.ProductResponse;
import com.example.gadegetarium.entity.Product;
import com.example.gadegetarium.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    @Query("SELECT NEW com.example.gadegetarium.dto.Product.ProductResponse(p.name, p.price,  p.characteristic, p.isFavorite, p.madeIn, p.category) FROM Product p")
    Page<ProductResponse> getAll(Pageable pageable);
    List<Product> findByCategory(Category category);
    @Query("SELECT p FROM Product p JOIN p.favorite f JOIN f.user u WHERE u.email = :userEmail")
    List<Product> findFavoriteProductsByUserEmail(@Param("userEmail") String userEmail);
    @Query("select new com.example.gadegetarium.dto.Product.ProductResponse(p.name,p.price, p.characteristic, p.isFavorite, p.madeIn,p.category)from Product p where p.category=:category order by p.price desc")
    List<ProductResponse> getProductByCategoryAndPriceDesc(Category category);
    @Query("select new com.example.gadegetarium.dto.Product.ProductResponse(p.name,p.price, p.characteristic, p.isFavorite, p.madeIn,p.category)from Product p  where p.category=:category order by p.price desc")
    List<ProductResponse> getProductByCategoryAndPriceAsc(Category category);

}
