package com.hackaton.hackatonv100.model.response;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class OperationResponse {

    private Long id;
    private Long member;
    private Date date;
    private int type;
    private int money;

}
