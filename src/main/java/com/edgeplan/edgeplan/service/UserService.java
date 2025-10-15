package com.edgeplan.edgeplan.service;

import com.edgeplan.edgeplan.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByEmail(String email);
    User save(User user);

    String currentUserEmail();
    User currentUser();
}