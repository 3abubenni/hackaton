package com.hackaton.hackatonv100.model.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MemberResponse {

    private Long id;
    private Long userId;
    private Long clanId;
    private int money;
    private int exp;
    private int status;
    private int solvedTasks;
    private int items;

}
