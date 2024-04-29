package com.hackaton.hackatonv100.service.impl;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.ClanRequest;
import com.hackaton.hackatonv100.security.repository.ClanRepository;
import com.hackaton.hackatonv100.service.IClanService;
import com.hackaton.hackatonv100.service.IMemberService;
import com.hackaton.hackatonv100.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClanService implements IClanService {

    private final ClanRepository clanRepository;
    private final IMemberService memberService;
    private final IUserService userService;

    @Autowired
    public ClanService(ClanRepository clanRepository, IMemberService memberService, IUserService userService) {
        this.clanRepository = clanRepository;
        this.memberService = memberService;
        this.userService = userService;
        memberService.setClanService(this);
    }

    @Override
    public Clan createClan(Principal principal, ClanRequest request) {
        Clan clan = Clan.builder()
                .name(request.getName())
                .build();

        clan = clanRepository.save(clan);
        User admin = userService.getUser(principal);
        memberService.createMember(
                clan, admin,
                Member.MemberStatus.ADMIN
        );

        return clan;
    }

    @Override
    public Clan updateClan(ClanRequest request, Long clanId) {
        Clan clan = clanRepository.findById(clanId).orElseThrow();
        clan.setName(request.getName());
        return clanRepository.save(clan);
    }

    @Override
    public boolean deleteClan(Principal principal, Long clanId) {
        if(memberService.userInClan(principal, clanId)) {
            User user = userService.getUser(principal);
            Clan clan = clanRepository.findById(clanId).orElseThrow();
            Member member = memberService.getMember(user, clan);
            if(member.checkStatus(Member.MemberStatus.CAN_DELETE_CLAN)) {
                deleteClan(clan);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean quitClan(Principal principal, Long clanId) {
        if(memberService.userInClan(principal, clanId)) {
            Clan clan = clanRepository.findById(clanId).orElseThrow();
            User user = userService.getUser(principal);
            Member member = memberService.getMember(user, clan);
            if(member.checkStatus(Member.MemberStatus.ADMIN)) {
                deleteClan(clan);

            } else {
                memberService.excludeMember(member);

            }
            return true;
        }

        return false;
    }

    @Override
    public Clan getClan(Long clanId) {
        return clanRepository.findById(clanId).orElseThrow();
    }

    @Override
    public List<Clan> getClansOfUser(Long userId) {
        User user = userService.getUser(userId);
        return getClansOfUser(user);
    }

    @Override
    public List<Clan> getClansOfUser(Principal principal) {
        User user = userService.getUser(principal);
        return getClansOfUser(user);
    }

    @Override
    public List<Clan> getClansOfUser(User user) {
        return memberService
                .getMembersOfUser(user)
                .stream().map(Member::getClan)
                .collect(Collectors.toList());
    }

    @Override
    public List<Clan> searchClan(String query) {
        return null;
    }

    @Override
    public boolean clanExists(Long clanId) {
        return clanRepository.existsById(clanId);
    }

    @Override
    public boolean userCanUpdateClan(Principal principal, Long clanId) {
        Member member = memberService.getMember(principal, clanId);
        return member.checkStatus(Member.MemberStatus.CAN_REDACT_CLAN);
    }

    private void deleteClan(Clan clan) {
        memberService.deleteMembersByClan(clan);
        clanRepository.delete(clan);
    }

}
