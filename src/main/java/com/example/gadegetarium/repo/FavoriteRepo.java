package com.example.gadegetarium.repo;

import com.example.gadegetarium.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
}
