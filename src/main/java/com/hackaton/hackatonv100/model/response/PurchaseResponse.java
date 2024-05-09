package com.hackaton.hackatonv100.model.response;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class PurchaseResponse {

    private Long id;
    private int money;
    private ItemDetailsResponse itemResponse;
    private Date date;
    private Long member;

}

