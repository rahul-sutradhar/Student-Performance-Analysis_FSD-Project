package com.studentperformance.backend.security;

import com.studentperformance.backend.security.dto.AdminProfileView;
import com.studentperformance.backend.security.dto.LoginCommand;
import com.studentperformance.backend.security.dto.LoginTokenView;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Open an administrator session and return a signed token")
    public LoginTokenView login(@Valid @RequestBody LoginCommand command) {
        return sessionService.openSession(command);
    }

    @GetMapping("/me")
    @Operation(summary = "Read the profile linked to the current token")
    public AdminProfileView me(Authentication authentication) {
        return sessionService.currentProfile(authentication);
    }
}
