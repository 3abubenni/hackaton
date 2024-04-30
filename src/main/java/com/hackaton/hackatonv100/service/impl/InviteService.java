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
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class InviteService implements IInviteService {

    private final IUserService userService;
    private final InviteRepository inviteRepository;
    private final IMemberService memberService;
    @Setter
    private IClanService clanService;


    @Autowired
    public InviteService(
            IUserService userService,
            InviteRepository inviteRepository,
            IMemberService memberService
    ) {
        this.userService = userService;
        this.inviteRepository = inviteRepository;
        this.memberService = memberService;
    }

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
    public void deleteAllByClan(Clan clan) {
        inviteRepository.deleteAllByClan(clan);
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
