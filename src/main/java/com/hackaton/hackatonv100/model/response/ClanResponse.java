package com.hackaton.hackatonv100.model.response;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Valid
@Getter
@Setter
@Builder
public class ClanResponse {

    private Long id;
    private String name;
    private String description;
    private String img;

}
