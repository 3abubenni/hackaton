package com.hackaton.hackatonv100.model.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@Getter
@Setter
@Builder
@Valid
@AllArgsConstructor
public class RegisterRequest {

    @NotNull
    @Size(min = 2)
    private String fname;
    @NotNull
    @Size(min = 2)
    private String lname;
    @Email
    private String email;
    @Size(min = 8)
    private String password;
    private Date bday;


}
