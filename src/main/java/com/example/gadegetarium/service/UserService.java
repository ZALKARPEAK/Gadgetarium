package com.example.gadegetarium.service;

import com.example.gadegetarium.dto.SimpleResponse;
import com.example.gadegetarium.dto.User.UserRequest;
import com.example.gadegetarium.dto.User.UserRequestInfo;
import com.example.gadegetarium.dto.User.UserResponseInfo;
import com.example.gadegetarium.dto.User.PaginationResponse;

public interface UserService {
    PaginationResponse getAllUsers(int page, int size);
    UserResponseInfo getUserById(UserRequest request);
    SimpleResponse updateUserByEmailAndPassword(UserRequestInfo requestInfo);
    SimpleResponse deleteUserById(Long id);
}
