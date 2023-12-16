package com.example.gadegetarium.repo;

import com.example.gadegetarium.dto.User.UserResponseInfo;
import com.example.gadegetarium.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT NEW com.example.gadegetarium.dto.User.UserResponseInfo(u.firstName, u.lastName, u.email, u.createDate, u.updateDate) FROM User u")
    Page<UserResponseInfo> getAllUser(Pageable pageable);

    Optional<User> findUserByEmail(String gmail);

    boolean existsByEmail(String gmail);
}
