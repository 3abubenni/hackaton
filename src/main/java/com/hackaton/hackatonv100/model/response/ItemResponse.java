package com.hackaton.hackatonv100.model.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ItemResponse {

    private Long id;
    private int cost;
    private String description;
    private String name;
    private String img;

}
