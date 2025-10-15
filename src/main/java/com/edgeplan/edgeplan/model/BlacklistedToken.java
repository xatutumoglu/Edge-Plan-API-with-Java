package com.edgeplan.edgeplan.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "blacklisted_tokens", indexes = @Index(name = "idx_blacklisted_token", columnList = "token"))
@Getter
@Setter
public class BlacklistedToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiresAt;
}