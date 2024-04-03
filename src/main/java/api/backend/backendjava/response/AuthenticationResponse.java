package api.backend.backendjava.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    String jwtToken;
    String message;

    UserResponse userResponse;

    public AuthenticationResponse(String message){
        this.message = message;
    }

}
