package com.hackaton.hackatonv100.model.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ItemDetailsResponse {

    private Long id;
    private int count;
    private int cost;
    private String name;
    private String description;
    private String img;

}
