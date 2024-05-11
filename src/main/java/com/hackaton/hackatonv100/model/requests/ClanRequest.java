package com.hackaton.hackatonv100.model.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@Valid
@AllArgsConstructor
@NoArgsConstructor
public class ClanRequest {

    @Size(min = 3, max = 40)
    private String name;
    private String description;

}
