package com.studentperformance.backend.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminDirectoryService implements UserDetailsService {

    private final AdminAccountRepository adminAccountRepository;

    public AdminDirectoryService(AdminAccountRepository adminAccountRepository) {
        this.adminAccountRepository = adminAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return adminAccountRepository.findByLoginName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Administrator not found: " + username));
    }
}
