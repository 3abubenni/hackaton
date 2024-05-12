package com.hackaton.hackatonv100.model.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@Getter
@Setter
@Valid
@Builder
public class TaskRequest {

    @Size(min = 3)
    private String name;
    @Size(min = 10)
    private String description;
    private int exp;
    private int money;



}
