package food_ordering_system.controller;

import food_ordering_system.dto.LoginRequest;
import food_ordering_system.dto.LoginResponse;
import food_ordering_system.dto.RegisterRequest;
import food_ordering_system.response.Response;
import food_ordering_system.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * AuthController handles authentication-related HTTP requests:
 * registration and login.
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * POST /api/auth/register
     * Registers a new user with the CUSTOMER role.
     * @Valid triggers validation on the incoming RegisterRequest.
     */
    @PostMapping("/register")
    public ResponseEntity<Response<Void>> register(
            @RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    /**
     * POST /api/auth/login
     * Authenticates a user and returns a signed JWT plus basic
     * profile info on success.
     */
    @PostMapping("/login")
    public ResponseEntity<Response<LoginResponse>> login(
            @RequestBody @Valid LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}