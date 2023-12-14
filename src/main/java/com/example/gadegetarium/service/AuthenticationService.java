package com.example.gadegetarium.service;


import com.example.gadegetarium.dto.Authentication.AuthenticationResponse;
import com.example.gadegetarium.dto.Authentication.SignInRequest;
import com.example.gadegetarium.dto.Authentication.SignUpRequest;

public interface AuthenticationService {
    AuthenticationResponse signUp(SignUpRequest request);
    AuthenticationResponse signIn(SignInRequest request);
}
