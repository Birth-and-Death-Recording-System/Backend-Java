package api.backend.backendjava.controller;

import api.backend.backendjava.request.LoginRequest;
import api.backend.backendjava.request.RegisterRequest;
import api.backend.backendjava.response.AuthenticationResponse;
import api.backend.backendjava.service.implementations.AuthServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthServiceImpl service;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register (@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(service.register(request));
        } catch (Exception e) {
            return new ResponseEntity<>(new AuthenticationResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
