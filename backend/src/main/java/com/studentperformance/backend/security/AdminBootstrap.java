package com.studentperformance.backend.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AdminBootstrap implements CommandLineRunner {

    private final AdminAccountRepository adminAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final String adminUsername;
    private final String adminPassword;

    public AdminBootstrap(
            AdminAccountRepository adminAccountRepository,
            PasswordEncoder passwordEncoder,
            @Value("${app.admin.username}") String adminUsername,
            @Value("${app.admin.password}") String adminPassword
    ) {
        this.adminAccountRepository = adminAccountRepository;
        this.passwordEncoder = passwordEncoder;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }

    @Override
    @Transactional
    public void run(String... args) {
        if (adminAccountRepository.findByLoginName(adminUsername).isPresent()) {
            return;
        }

        AdminAccount account = new AdminAccount();
        account.setLoginName(adminUsername);
        account.setPasswordHash(passwordEncoder.encode(adminPassword));
        account.setAccessTier(AccessTier.ADMIN);
        adminAccountRepository.save(account);
    }
}
