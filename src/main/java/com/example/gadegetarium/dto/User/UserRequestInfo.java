package com.example.gadegetarium.dto.User;

import java.time.ZonedDateTime;

public record UserRequestInfo(
        String firstName,
        String lastName,
        String email,
        String password,
        ZonedDateTime createdAt,
        ZonedDateTime updatedAt
) {
}
