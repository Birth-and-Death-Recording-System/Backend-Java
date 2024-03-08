package api.backend.backendjava.service.interfaces;

import api.backend.backendjava.request.LoginRequest;
import api.backend.backendjava.request.RegisterRequest;
import api.backend.backendjava.response.AuthenticationResponse;

public interface AuthInterface {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse login(LoginRequest request);
}
