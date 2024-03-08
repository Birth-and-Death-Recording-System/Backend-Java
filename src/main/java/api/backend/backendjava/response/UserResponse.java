package api.backend.backendjava.response;

public record UserResponse (
    Long id,
    String username,
    String email,
    String role,
    String gender
){}
