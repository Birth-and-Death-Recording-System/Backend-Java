package api.backend.backendjava.service.implementations;

import api.backend.backendjava.entity.User;
import api.backend.backendjava.entity.enums.Gender;
import api.backend.backendjava.entity.enums.Role;
import api.backend.backendjava.request.LoginRequest;
import api.backend.backendjava.request.RegisterRequest;
import api.backend.backendjava.response.AuthenticationResponse;
import api.backend.backendjava.repository.UserRepository;
import api.backend.backendjava.response.UserResponse;
import api.backend.backendjava.service.interfaces.AuthInterface;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthInterface {
    private final UserRepository repository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtServiceImpl jwtService;


    public AuthenticationResponse register(@NotNull RegisterRequest request) {
        Optional<User> userExistsByUsername = repository.findByUsername(request.username());
        Optional<User> userExistsByEmail = repository.findByEmail(request.email());
        if (userExistsByUsername.isPresent() && userExistsByEmail.isPresent()){
            throw new IllegalStateException("Username or Email already exists");
        }

        // Retrieve user details from the repository
        User newUser = repository.save(User.builder()
                .username(request.username())
                .email(request.email())
                .role(Role.valueOf(request.role()))
                .gender(Gender.valueOf(request.gender()))
                .password(passwordEncoder.encode(request.password()))
                .enabled(true)
                .locked(true)
                .build());

        UserResponse user = getUser(newUser);
        String jwtToken = jwtService.generateToken(String.valueOf(user));

        // Generate a token
        String token = UUID.randomUUID().toString();

        // Build the authentication response
        return getAuthenticationResponse(token,  user, "Registration successful", jwtToken);

    }

    public AuthenticationResponse login(LoginRequest request) {
        Authentication authenticate;
        try {
            authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.username(),
                    request.password()));

            User newUser = repository.findByUsername(request.username())
                    .orElseThrow();

            if (authenticate.isAuthenticated()) {
                UserResponse user = getUser(newUser);

                // Generate a token
                String token = UUID.randomUUID().toString();
                String jwtToken = jwtService.generateToken(String.valueOf(user));

                return getAuthenticationResponse(token, user, "Login successful", jwtToken);
            }
        } catch (Exception e) {
            throw new IllegalStateException("Invalid username or password");
        }

        throw new IllegalStateException("Authentication failed");
    }


//    Responses
    @NotNull
    private static UserResponse getUser(@NotNull User newUser) {
        return new UserResponse(
                newUser.getId(), newUser.getEmail(),
                newUser.getUsername(),
                String.valueOf(newUser.getRole()),
                String.valueOf(newUser.getGender()));
    }


    private static AuthenticationResponse getAuthenticationResponse(String token, UserResponse user, String message, String jwtToken) {
        return AuthenticationResponse.builder()
                .token(token)
                .jwtToken(jwtToken)
                .userResponse(user)
                .message(message)
                .build();
    }

}
