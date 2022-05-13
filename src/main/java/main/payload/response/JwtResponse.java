package main.payload.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String address;
    private String phone;
    private List<String> roles;

    public JwtResponse(String token,
                       Long id,
                       String username,
                       String email,
                       String fullName,
                       String address,
                       String phone,
                       List<String> roles) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.roles = roles;
    }
}
