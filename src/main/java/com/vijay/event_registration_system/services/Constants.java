package com.vijay.event_registration_system.services;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

public class Constants {
    // Method to generate a secret key for HS256 algorithm
    public static SecretKey generateSecretKey() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }
    private static final String SECRET_KEY_STRING = "EVENTREGISTRATIONSYSTEM";

    public static final SecretKey API_SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Constants for token validity in milliseconds
    public static final long TOKEN_VALIDITY = 2 * 60 * 60 * 1000;
}

