package com.hackaton.hackatonv100.model.requests;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@Data
@AllArgsConstructor
public class UserUpdateRequest {

    @Size(min = 2)
    private String fname;
    @Size(min = 2)
    private String lname;
    private Date bday;

}
