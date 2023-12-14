package com.example.gadegetarium.service.impl;

import com.example.gadegetarium.Exception.NotFoundException;
import com.example.gadegetarium.dto.SimpleResponse;
import com.example.gadegetarium.dto.User.PaginationResponse;
import com.example.gadegetarium.dto.User.UserRequestInfo;
import com.example.gadegetarium.dto.User.UserResponseInfo;
import com.example.gadegetarium.entity.Basket;
import com.example.gadegetarium.entity.Comment;
import com.example.gadegetarium.entity.Favorite;
import com.example.gadegetarium.entity.User;
import com.example.gadegetarium.repo.BasketRepo;
import com.example.gadegetarium.repo.CommentRepo;
import com.example.gadegetarium.repo.FavoriteRepo;
import com.example.gadegetarium.repo.UserRepo;
import com.example.gadegetarium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final BasketRepo basketRepo;
    private final CommentRepo commentRepo;
    private final FavoriteRepo favoriteRepo;

    @Override
    public PaginationResponse getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<UserResponseInfo> getAllUsers = userRepo.getAllUser(pageable);
        log.info("Get all users method is able...");
        return PaginationResponse.builder()
                .userResponseInfoList(getAllUsers.getContent())
                .currentPage(getAllUsers.getNumber() + 1)
                .pageSize(getAllUsers.getTotalPages())
                .build();
    }

    @Override
    public UserResponseInfo getUserById(String email) {
        User user = userRepo.findUserByEmail(email).orElseThrow(
                () -> new NotFoundException("User not found"));

        return UserResponseInfo.builder().firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .createdAt(user.getCreateDate())
                .updatedAt(user.getUpdateDate()).build();
    }

    @Override
    public SimpleResponse updateUserByEmailAndPassword(UserRequestInfo requestInfo) {
        User authenticatedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!authenticatedUser.getEmail().equals(requestInfo.email())) {
            throw new AccessDeniedException("You don't have permission to update this user");
        }

        User user = userRepo.findUserByEmail(requestInfo.email()).orElseThrow(
                () -> new NotFoundException("User not found"));

        user.setFirstName(requestInfo.firstName());
        user.setLastName(requestInfo.lastName());
        user.setPassword(requestInfo.password());
        user.setUpdateDate(ZonedDateTime.now());

        userRepo.save(user);

        return new SimpleResponse(HttpStatus.OK, "User updated successfully");
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new NotFoundException("User not found"));
        Basket basket = user.getBasket();
        List<Comment> comment = user.getComments();
        List<Favorite> favorites = user.getFavorites();
        user.setBasket(null);
        user.setComments(null);
        user.setFavorites(null);
        basketRepo.delete(basket);
        commentRepo.delete((Comment) comment);
        favoriteRepo.delete((Favorite) favorites);
        userRepo.delete(user);
        return new SimpleResponse(HttpStatus.OK, "user was deleted");
    }
}
