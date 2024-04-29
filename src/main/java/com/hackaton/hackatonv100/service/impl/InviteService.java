package com.hackaton.hackatonv100.service.impl;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Invite;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.enums.States;
import com.hackaton.hackatonv100.repository.InviteRepository;
import com.hackaton.hackatonv100.service.IClanService;
import com.hackaton.hackatonv100.service.IInviteService;
import com.hackaton.hackatonv100.service.IMemberService;
import com.hackaton.hackatonv100.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class InviteService implements IInviteService {

    private IUserService userService;
    private InviteRepository inviteRepository;
    private IMemberService memberService;
    private IClanService clanService;

    @Override
    public Invite inviteUser(Long inventedId, Long clanId) {
        var user = userService.getUser(inventedId);
        var clan = clanService.getClan(clanId);
        return inviteUser(user, clan);
    }

    @Override
    public Invite inviteUser(User invented, Clan clan) {
        inviteRepository.deleteAllByUser(invented);
        var invite = Invite.builder()
                .clan(clan)
                .user(invented)
                .state(States.CREATED.code)
                .build();

        return inviteRepository.save(invite);
    }

    @Override
    public Invite acceptInvite(Invite invite) {
        memberService.createMember(
                invite.getClan(),
                invite.getUser(),
                Member.MemberStatus.MEMBER
        );

        invite.setState(States.ACCEPTED);
        return inviteRepository.save(invite);
    }

    @Override
    public Invite cancelInvite(Invite invite) {
        invite.setState(States.CANCELED);
        return inviteRepository.save(invite);
    }

    @Override
    public void deleteInvite(Long inviteId) {
        inviteRepository.deleteById(inviteId);
    }

    @Override
    public boolean inviteExists(Long inviteId) {
        return inviteRepository.existsById(inviteId);
    }

    @Override
    public Invite getInvite(Long inviteId) {
        return inviteRepository.findById(inviteId).orElseThrow();
    }

    @Override
    public List<Invite> invitesOfUser(Principal principal) {
        var user = userService.getUser(principal);
        return inviteRepository.findByUser(user);
    }

    @Override
    public List<Invite> invitesOfClan(Long clanId) {
        var clan = clanService.getClan(clanId);
        return inviteRepository.findByClan(clan);
    }
}
