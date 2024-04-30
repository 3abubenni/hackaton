package com.hackaton.hackatonv100.model.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
@Valid
public class TaskRequest {

    @Size(min = 3)
    private String name;
    @Size(min = 20)
    private String description;
    private int exp;
    private int money;



}
