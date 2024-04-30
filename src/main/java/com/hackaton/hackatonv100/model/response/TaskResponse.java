package com.hackaton.hackatonv100.model.response;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TaskResponse {

    private Long id;
    private Long memberId;
    private Long clanId;
    private String description;
    private String name;
    private int money;
    private int exp;
    private Date createdAt;
    private boolean required;
    private int status;

}

