package com.hackaton.hackatonv100.model.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ApplicationResponse {

    private Long id;
    private Long userId;
    private Long clanId;
    private int state;

}
