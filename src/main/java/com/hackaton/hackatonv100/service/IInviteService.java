package com.hackaton.hackatonv100.service;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Invite;
import com.hackaton.hackatonv100.model.User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface IInviteService {

    void setClanService(IClanService clanService);
    Invite inviteUser(Long inventedId, Long clanId);
    Invite inviteUser(User invented, Clan clan);
    Invite acceptInvite(Invite invite);
    Invite cancelInvite(Invite invite);
    void deleteInvite(Long inviteId);
    boolean inviteExists(Long inviteId);
    Invite getInvite(Long inviteId);
    List<Invite> invitesOfUser(Principal principal);
    List<Invite> invitesOfClan(Long clanId);


    void deleteAllByIdClan(long id);
}
