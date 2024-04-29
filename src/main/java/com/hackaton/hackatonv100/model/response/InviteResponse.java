package com.hackaton.hackatonv100.model.response;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InviteResponse {

    private Long id;
    private Long idUser;
    private Long idClan;
    private int state;
    private int type;



}
