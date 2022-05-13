package main.payload.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class OwnerRequest {

    @NotBlank
    @Size(min = 2, max = 150)
    private String fullName;

    @NotBlank
    @Size(max = 200)
    private String address;

    @NotBlank
    @Size(max = 15)
    private String phone;

    @NotBlank
    @Email
    private String email;

}
