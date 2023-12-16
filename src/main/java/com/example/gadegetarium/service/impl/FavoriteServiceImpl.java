package com.example.gadegetarium.service.impl;

import com.example.gadegetarium.Exception.NotFoundException;
import com.example.gadegetarium.dto.Favorite.FavoriteResponse;
import com.example.gadegetarium.dto.SimpleResponse;
import com.example.gadegetarium.entity.Favorite;
import com.example.gadegetarium.entity.Product;
import com.example.gadegetarium.entity.User;
import com.example.gadegetarium.repo.FavoriteRepo;
import com.example.gadegetarium.repo.ProductRepo;
import com.example.gadegetarium.repo.UserRepo;
import com.example.gadegetarium.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepo favoriteRepo;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;

    @Override
    public SimpleResponse clickFavorite(Long userId, Long productId) {
        User user = userRepo.findById(userId).orElseThrow(() ->
                new NotFoundException("User not found"));
        Product product = productRepo.findById(productId).orElseThrow(() ->
                new NotFoundException("Product not found"));

        if(user != null && product != null) {
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setProduct(product);
            favoriteRepo.save(favorite);
        }
        return SimpleResponse.builder().message("OK").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public List<FavoriteResponse> getAllFavoritesFromUser(Long userId) {
        return favoriteRepo.getAllFavoritesFromUser(userId);
    }
}
