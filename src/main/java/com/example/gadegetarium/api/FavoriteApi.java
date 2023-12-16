package com.example.gadegetarium.api;


import com.example.gadegetarium.dto.Favorite.FavoriteResponse;
import com.example.gadegetarium.dto.SimpleResponse;
import com.example.gadegetarium.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/favorite")
@RequiredArgsConstructor
public class FavoriteApi {

    private final FavoriteService favoriteService;

    @PostMapping("/like/{userId}/{productId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public ResponseEntity<SimpleResponse> saveLikeOrDislike(@PathVariable Long userId, @PathVariable Long productId){
        return ResponseEntity.ok(favoriteService.clickFavorite(userId, productId));
    }


    @PostMapping("/getAllLikes/{userId}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public List<FavoriteResponse> getFavorite(@PathVariable Long userId){
        return favoriteService.getAllFavoritesFromUser(userId);
    }
}

