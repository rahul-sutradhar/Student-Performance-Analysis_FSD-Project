package com.studentperformance.backend.security;

import com.studentperformance.backend.security.dto.AuthRequest;
import com.studentperformance.backend.security.dto.AuthResponse;
import com.studentperformance.backend.security.dto.CurrentUserResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AuthResponse login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.username(), request.password())
        );

        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token = jwtService.generateToken(appUser);

        return new AuthResponse(token, appUser.getUsername(), appUser.getRole().name());
    }

    public CurrentUserResponse getCurrentUser(Authentication authentication) {
        AppUser appUser = (AppUser) authentication.getPrincipal();
        return new CurrentUserResponse(appUser.getUsername(), appUser.getRole().name());
    }
}
