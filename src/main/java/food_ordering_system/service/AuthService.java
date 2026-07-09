package food_ordering_system.service;

import food_ordering_system.dto.LoginRequest;
import food_ordering_system.dto.LoginResponse;
import food_ordering_system.dto.RegisterRequest;
import food_ordering_system.response.Response;

/**
 * AuthService defines the authentication-related business operations:
 * registering a new user and logging one in.
 */
public interface AuthService {

    /**
     * Registers a new user. Hashes the password, assigns the CUSTOMER
     * role automatically, and rejects the request if the email is
     * already registered.
     */
    Response<Void> register(RegisterRequest request);

    /**
     * Authenticates a user by email and password, returning a signed
     * JWT plus basic profile info on success.
     */
    Response<LoginResponse> login(LoginRequest request);
}