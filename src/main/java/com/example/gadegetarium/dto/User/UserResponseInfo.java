package com.example.gadegetarium.dto.User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
@Getter
@Setter
@Builder
public class UserResponseInfo{
        private String firstName;
        private String lastName;
        private String email;
        private ZonedDateTime createdAt;
        private ZonedDateTime updatedAt;

    public UserResponseInfo(String firstName, String lastName, String email, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
