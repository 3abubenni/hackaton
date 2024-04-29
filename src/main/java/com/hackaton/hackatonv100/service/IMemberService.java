package com.hackaton.hackatonv100.service;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.MemberRequest;
import com.hackaton.hackatonv100.service.impl.ClanService;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface IMemberService {

    Member createMember(Clan clan, User user, Member.MemberStatus status);
    Member changeMemberStatus(Member member, Member.MemberStatus status);
    Member changeMemberStatus(Member member, int status);
    Member getMember(Long id);
    boolean memberExists(Long id);
    Member getMember(Principal principal, Long clanId);
    Member updateMember(Long memberId, MemberRequest request);
    Member updateMember(Member member, MemberRequest request);
    List<Member> getMembersOfClan(Long idClan);
    void excludeMember(Long idMember);
    void excludeMember(Member member);
    List<Member> getMembersOfUser(User user);
    List<Member> getMembersOfUser(Principal principal);
    List<Member> getMembersOfUser(Long userId);
    boolean userInClan(User user, Clan clan);
    boolean userInClan(Principal principal, Long clanId);
    boolean userHaveStatusInClan(Principal principal, Long clanId, Member.MemberStatus status);
    boolean userHaveStatusInClan(Principal principal, Clan clan, Member.MemberStatus status);
    void setClanService(ClanService clanService);
    Member getMember(User user, Clan clan);
    Member getMember(Principal principal, Clan clan);
    void deleteMembersByClan(Clan clan);

    boolean kickOutMember(Principal principal, Long memberId);
}