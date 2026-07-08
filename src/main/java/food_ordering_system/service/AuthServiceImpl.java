package food_ordering_system.service;

import food_ordering_system.dto.LoginRequest;
import food_ordering_system.dto.LoginResponse;
import food_ordering_system.dto.RegisterRequest;
import food_ordering_system.entity.Role;
import food_ordering_system.entity.User;
import food_ordering_system.exception.EmailAlreadyExistsException;
import food_ordering_system.exception.InactiveAccountException;
import food_ordering_system.exception.InvalidCredentialsException;
import food_ordering_system.repository.RoleRepository;
import food_ordering_system.repository.UserRepository;
import food_ordering_system.response.Response;
import food_ordering_system.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * AuthServiceImpl contains the business logic for registration and
 * login. Passwords are always BCrypt-hashed before saving, and every
 * new user is automatically assigned the CUSTOMER role - the client
 * never gets to choose their own role.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    private static final String GENERIC_LOGIN_ERROR = "Invalid email or password";

    @Override
    public Response<Void> register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailAlreadyExistsException(
                    "An account with this email already exists");
        }

        Role customerRole = roleRepository.findByName("CUSTOMER")
                .orElseThrow(() -> new IllegalStateException(
                        "CUSTOMER role is missing - check RoleSeeder"));

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .active(true)
                .roles(List.of(customerRole))
                .build();

        userRepository.save(user);

        return Response.success("User registered successfully", null);
    }

    @Override
    public Response<LoginResponse> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException(GENERIC_LOGIN_ERROR));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException(GENERIC_LOGIN_ERROR);
        }

        if (!user.isActive()) {
            throw new InactiveAccountException(
                    "This account is inactive. Please contact support.");
        }

        String token = jwtUtils.generateToken(user.getEmail());

        List<String> roleNames = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        LoginResponse loginResponse = new LoginResponse(
                token, user.getEmail(), user.getName(), roleNames);

        return Response.success("Login successful", loginResponse);
    }
}