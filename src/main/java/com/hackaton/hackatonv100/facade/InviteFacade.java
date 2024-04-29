package com.hackaton.hackatonv100.facade;

import com.hackaton.hackatonv100.model.Invite;
import com.hackaton.hackatonv100.model.response.InviteResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class InviteFacade {

    public InviteResponse inviteToInviteResponse(Invite invite) {
        return InviteResponse.builder()
                .id(invite.getId())
                .idClan(invite.getClan().getId())
                .idUser(invite.getUser().getId())
                .state(invite.getState())
                .build();
    }

    public List<InviteResponse> invitesToInvitesResponse(Collection<Invite> invites) {
        return invites.stream()
                .map(this::inviteToInviteResponse)
                .collect(Collectors.toList());

    }

}
