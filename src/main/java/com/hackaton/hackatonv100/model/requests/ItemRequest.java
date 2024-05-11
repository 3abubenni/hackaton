package com.hackaton.hackatonv100.model.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Valid
@Builder
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
