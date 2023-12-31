package com.example.gadegetarium.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.gadegetarium.entity.User;
import com.example.gadegetarium.repo.UserRepo;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepo userRepository;

   /* @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer")) {
            String token = tokenHeader.substring(7);
            if (StringUtils.hasText(token)) {
            try {
                    String username = jwtService.validateToken(token);
                    User user = userRepository.findUserByEmail(username)
                            .orElseThrow(() ->
                                    new EntityNotFoundException("user with email: " + username + " not found"));

                    SecurityContextHolder.getContext()
                            .setAuthentication(
                                    new UsernamePasswordAuthenticationToken(
                                            user.getEmail(),
                                            null,
                                            user.getAuthorities()));

                } catch(JWTVerificationException e){
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid Token");
                }
            }

        }

        filterChain.doFilter(request, response);
    }*/

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer")) {
            String token = tokenHeader.substring(7);
            if (StringUtils.hasText(token)) {
                try {
                    String username = jwtService.validateToken(token);
                    User user = userRepository.findUserByEmail(username)
                            .orElseThrow(() ->
                                    new EntityNotFoundException("user with email: " + username + " not found"));

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    user,
                                    null,
                                    user.getAuthorities());

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                } catch (JWTVerificationException e) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid Token");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
