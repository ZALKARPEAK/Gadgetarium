package com.example.gadegetarium.repo;

import com.example.gadegetarium.dto.Favorite.FavoriteResponse;
import com.example.gadegetarium.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
    @Query("select new com.example.gadegetarium.dto.Favorite.FavoriteResponse(f.id,p.name,u.lastName) from Favorite f join f.product p join f.user u where p.id =:productId")
    List<FavoriteResponse> getAllFavoritesFromUser(@Param("productId") Long productId);

}
