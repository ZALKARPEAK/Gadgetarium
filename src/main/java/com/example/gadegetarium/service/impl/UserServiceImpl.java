package com.example.gadegetarium.service.impl;

import com.example.gadegetarium.Exception.NotFoundException;
import com.example.gadegetarium.dto.SimpleResponse;
import com.example.gadegetarium.dto.User.PaginationResponse;
import com.example.gadegetarium.dto.User.UserRequest;
import com.example.gadegetarium.dto.User.UserRequestInfo;
import com.example.gadegetarium.dto.User.UserResponseInfo;
import com.example.gadegetarium.entity.Basket;
import com.example.gadegetarium.entity.User;
import com.example.gadegetarium.repo.BasketRepo;
import com.example.gadegetarium.repo.UserRepo;
import com.example.gadegetarium.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final BasketRepo basketRepo;

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
    public UserResponseInfo getUserById(UserRequest request) {
        User user = userRepo.findUserByEmail(request.email()).orElseThrow(
                () -> new NotFoundException("User not found"));

        return UserResponseInfo.builder().firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .createdAt(user.getCreateDate())
                .updatedAt(user.getUpdateDate()).build();
    }

    @Override
    public SimpleResponse updateUserByEmailAndPassword(UserRequestInfo requestInfo) {
        User user = userRepo.findUserByEmail(requestInfo.email()).orElseThrow(
                () -> new NotFoundException("User not found"));

        User newUser = new User();
        newUser.setFirstName(requestInfo.firstName());
        newUser.setLastName(requestInfo.lastName());
        newUser.setEmail(user.getEmail());
        newUser.setRole(user.getRole());
        newUser.setPassword(requestInfo.password());
        newUser.setCreateDate(user.getCreateDate());
        newUser.setUpdateDate(ZonedDateTime.now());

        userRepo.save(newUser);

        return new SimpleResponse(HttpStatus.OK, "User updated successfully");
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        User user = userRepo.findById(id).orElseThrow(() ->
                new NotFoundException("User not found"));
        user.setBasket(null);
        user.setComments(null);
        user.setFavorites(null);
        userRepo.delete(user);
        return new SimpleResponse(HttpStatus.OK, "user was deleted");
    }
}
