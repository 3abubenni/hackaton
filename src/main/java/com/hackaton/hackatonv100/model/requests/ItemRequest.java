package com.hackaton.hackatonv100.model.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Valid
public class ItemRequest {

    @Size(min = 3, max = 40)
    private String name;
    private String description;
    private int cost;
    private int amount;

    public boolean isValid() {
        return cost >= 0 && amount >= 0;
    }

}
