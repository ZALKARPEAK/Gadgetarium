package com.example.gadegetarium.service;

import com.example.gadegetarium.dto.Favorite.FavoriteResponse;
import com.example.gadegetarium.dto.SimpleResponse;

import java.util.List;

public interface FavoriteService {
    SimpleResponse clickFavorite(Long userId, Long productId);
    List<FavoriteResponse> getAllFavoritesFromUser(Long userId);

}
