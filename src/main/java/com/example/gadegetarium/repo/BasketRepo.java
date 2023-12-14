package com.example.gadegetarium.repo;

import com.example.gadegetarium.entity.Basket;
import com.example.gadegetarium.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BasketRepo extends JpaRepository<Basket, Long> {
    Optional<Basket> findByUserId(Long id);
}
