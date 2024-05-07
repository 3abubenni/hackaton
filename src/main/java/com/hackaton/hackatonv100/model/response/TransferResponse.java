package com.hackaton.hackatonv100.model.response;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TransferResponse {

    private int money;
    private Long id;
    private Long memberFrom;
    private Long memberTo;
    private Date date;

}
