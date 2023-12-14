package com.example.gadegetarium.dto.User;


public record UserRequestInfo(
        String firstName,
        String lastName,
        String email,
        String password
) {
}
