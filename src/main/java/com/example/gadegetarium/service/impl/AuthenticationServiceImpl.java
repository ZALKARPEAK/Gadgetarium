package com.example.gadegetarium.service.impl;

import com.example.gadegetarium.Exception.AlreadyExistsException;
import com.example.gadegetarium.Exception.BadCredentialsException;
import com.example.gadegetarium.config.JwtService;
import com.example.gadegetarium.dto.Authentication.AuthenticationResponse;
import com.example.gadegetarium.dto.Authentication.SignInRequest;
import com.example.gadegetarium.dto.Authentication.SignUpRequest;
import com.example.gadegetarium.entity.Basket;
import com.example.gadegetarium.entity.User;
import com.example.gadegetarium.repo.UserRepo;
import com.example.gadegetarium.service.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthenticationResponse signUp(SignUpRequest request) {
        if(userRepo.existsByEmail(request.getEmail())){
            throw new AlreadyExistsException(String.format("User with email: %s already exists", request.getEmail()));
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setCreateDate(ZonedDateTime.now());

        Basket basket = user.getBasket();
        if (basket == null) {
            basket = new Basket();
            basket.setUser(user);
            user.setBasket(basket);
        }

        userRepo.save(user);

        String jwt = jwtService.generateToken(user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setEmail(user.getEmail());
        authenticationResponse.setRole(user.getRole());
        authenticationResponse.setToken(jwt);
        return authenticationResponse;
    }

    @Override
    public AuthenticationResponse signIn(SignInRequest request) {
        User user = userRepo.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new NoSuchElementException(
                        "User with email: " + request.getEmail() + " not found"
                ));

        if (request.getEmail().isBlank()) {
            throw new BadCredentialsException("Email doesn't exist");
        }

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        String jwt = jwtService.generateToken(user);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setEmail(user.getEmail());
        authenticationResponse.setRole(user.getRole());
        authenticationResponse.setToken(jwt);

        return authenticationResponse;
    }
}
