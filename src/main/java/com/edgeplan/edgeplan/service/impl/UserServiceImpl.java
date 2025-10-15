package com.edgeplan.edgeplan.service.impl;

import com.edgeplan.edgeplan.repository.UserRepository;
import com.edgeplan.edgeplan.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository repo;

    @Override
    public Optional<com.edgeplan.edgeplan.model.User> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public com.edgeplan.edgeplan.model.User save(com.edgeplan.edgeplan.model.User user) {
        return repo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        com.edgeplan.edgeplan.model.User u = repo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(u.getEmail())
                .password(u.getPassword())
                .authorities("USER")
                .build();
    }

    @Override
    public String currentUserEmail() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth.getName() : null;
    }

    @Override
    public com.edgeplan.edgeplan.model.User currentUser() {
        String email = currentUserEmail();
        if (email == null)
            throw new UsernameNotFoundException("No authenticated user");
        return findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}