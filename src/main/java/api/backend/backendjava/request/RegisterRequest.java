package api.backend.backendjava.request;



public record RegisterRequest (
     String username,
     String email,
     String password,
     String role,
     String gender

){}
