package com.hackaton.hackatonv100.model.requests;

import jakarta.validation.Valid;
import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Valid
public class MemberRequest {


    private int money;
    private int exp;

}
