package com.hackaton.hackatonv100.model.response;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UserResponse {

    private Long id;
    private String fname;
    private String lname;
    private String email;
    private Date bday;

}
