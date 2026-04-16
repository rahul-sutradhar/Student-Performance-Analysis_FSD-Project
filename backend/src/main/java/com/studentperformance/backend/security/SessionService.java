package com.studentperformance.backend.security;

import com.studentperformance.backend.security.dto.AdminProfileView;
import com.studentperformance.backend.security.dto.LoginCommand;
import com.studentperformance.backend.security.dto.LoginTokenView;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    private final AuthenticationManager authenticationManager;
    private final TokenIssuer tokenIssuer;

    public SessionService(AuthenticationManager authenticationManager, TokenIssuer tokenIssuer) {
        this.authenticationManager = authenticationManager;
        this.tokenIssuer = tokenIssuer;
    }

    public LoginTokenView openSession(LoginCommand command) {
        Authentication authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken.unauthenticated(command.getUsername(), command.getPassword())
        );

        AdminAccount adminAccount = (AdminAccount) authentication.getPrincipal();
        String token = tokenIssuer.issue(adminAccount);

        return new LoginTokenView(token, adminAccount.getUsername(), adminAccount.getAccessTier().authorityName());
    }

    public AdminProfileView currentProfile(Authentication authentication) {
        AdminAccount adminAccount = (AdminAccount) authentication.getPrincipal();
        return new AdminProfileView(adminAccount.getUsername(), adminAccount.getAccessTier().authorityName());
    }
}
