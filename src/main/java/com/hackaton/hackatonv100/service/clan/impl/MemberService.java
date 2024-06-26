package com.hackaton.hackatonv100.service.clan.impl;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Task;
import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.MemberRequest;
import com.hackaton.hackatonv100.repository.MemberRepository;
import com.hackaton.hackatonv100.service.clan.IClanService;
import com.hackaton.hackatonv100.service.clan.IMemberService;
import com.hackaton.hackatonv100.service.clan.ITaskService;
import com.hackaton.hackatonv100.service.user.IUserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService implements IMemberService {

    private final MemberRepository memberRepository;
    private final IUserService userService;
    @Setter
    private IClanService clanService;
    @Setter
    private ITaskService taskService;

    @Autowired
    public MemberService(
            MemberRepository memberRepository,
            IUserService userService
    ) {
        this.memberRepository = memberRepository;
        this.userService = userService;
    }

    @Override
    public Member createMember(Clan clan, User user, Member.MemberStatus status) {
        var member = Member.builder()
                .user(user)
                .clan(clan)
                .status(status.code)
                .build();

        return memberRepository.save(member);
    }

    @Override
    public Member changeMemberStatus(Member member, Member.MemberStatus status) {
        changeAdminIfStatusAdmin(status.code, member);
        member.setStatus(status.code);
        return memberRepository.save(member);
    }

    @Override
    public Member changeMemberStatus(Member member, int status) {
        changeAdminIfStatusAdmin(status, member);
        member.setStatus(status);
        return memberRepository.save(member);
    }

    @Override
    public Member getMember(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    @Override
    public boolean memberExists(Long id) {
        return memberRepository.existsById(id);
    }

    @Override
    public Member getMember(Principal principal, Long clanId) {
        var user = userService.getUser(principal);
        var clan = clanService.getClan(clanId);
        return memberRepository.findByUserAndClan(user, clan)
                .orElseThrow();
    }

    @Override
    public Member updateMember(Long memberId, MemberRequest request) {
        var member = memberRepository.findById(memberId).orElseThrow();
        return updateMember(member, request);
    }

    @Override
    public Member updateMember(Member member, MemberRequest request) {
        member.setExp(request.getExp());
        member.setMoney(request.getMoney());
        return memberRepository.save(member);
    }

    @Override
    public List<Member> getMembersOfClan(Long idClan) {
        return memberRepository.findAllByIdClan(idClan);
    }

    @Override
    public void excludeMember(Long idMember) {
        var member = getMember(idMember);
        excludeMember(member);
    }

    @Override
    public void excludeMember(Member member) {
        var tasks = taskService.getTasksOfMember(member)
                .stream().peek(t -> {
                    if(t.getStatus() != Task.SolutionStatus.CHECKED.num) {
                        t.setStatus(Task.SolutionStatus.CREATED);
                    }
                    t.setSolver(null);
                }).collect(Collectors.toList());


        taskService.saveAllTasks(tasks);
        memberRepository.delete(member);
    }

    @Override
    public List<Member> getMembersOfUser(User user) {
        return memberRepository.findAllByUser(user);
    }

    @Override
    public List<Member> getMembersOfUser(Principal principal) {
        var user = userService.getUser(principal);
        return getMembersOfUser(user);
    }

    @Override
    public List<Member> getMembersOfUser(Long userId) {
        var user = userService.getUser(userId);
        return getMembersOfUser(user);
    }

    @Override
    public boolean userInClan(User user, Clan clan) {
        return memberRepository.existsByUserAndClan(user, clan);
    }

    @Override
    public boolean userInClan(Principal principal, Long clanId) {
        var user = userService.getUser(principal);
        var clan = clanService.getClan(clanId);
        return userInClan(user, clan);
    }

    @Override
    public boolean userHaveStatusInClan(Principal principal, Long clanId, Member.MemberStatus status) {
        if(userInClan(principal, clanId)) {
            var member = getMember(principal, clanId);
            return member.checkStatus(status);
        }
        return false;
    }

    @Override
    public boolean userHaveStatusInClan(Principal principal, Clan clan, Member.MemberStatus status) {
        var user = userService.getUser(principal);
        return userHaveStatusInClan(user, clan, status);
    }

    @Override
    public boolean userHaveStatusInClan(User user, Clan clan, Member.MemberStatus status) {
        if(userInClan(user, clan)) {
            var member = getMember(user, clan);
            return member.checkStatus(status);
        }
        return false;
    }

    @Override
    public Member getMember(User user, Clan clan) {
        return memberRepository.findByUserAndClan(user, clan).orElseThrow();
    }

    @Override
    public Member getMember(Principal principal, Clan clan) {
        var user = userService.getUser(principal);
        return getMember(user, clan);
    }


    @Override
    public boolean kickOutMember(Principal principal, Long memberId) {
        var member = memberRepository.findById(memberId).orElseThrow();
        var user = userService.getUser(principal);

        if (member.getUser().equals(user)) {
            return false;
        }

        if (!userInClan(user, member.getClan())) {
            return false;
        }

        var kicking = getMember(user, member.getClan());
        if (!kicking.checkStatus(Member.MemberStatus.CAN_KICK_OUT) || member.checkStatus(Member.MemberStatus.ADMIN)) {
            return false;

        } else {
            excludeMember(member);
            return true;

        }
    }

    @Override
    public List<Member> membersOfClan(Long clanId) {
        return memberRepository.findAllByIdClan(clanId);
    }

    @Override
    public boolean userHasClanAsAdmin(Principal principal) {
        var user = userService.getUser(principal);
        return memberRepository.userHasClanWithStatus(user, Member.MemberStatus.ADMIN.code);
    }

    private void changeAdminIfStatusAdmin(int status, Member member) {
        if (status == Member.MemberStatus.ADMIN.code) {
            Member exAdmin = memberRepository.findByStatusAndClan(status, member.getClan()).orElseThrow();
            exAdmin.setStatus(Member.MemberStatus.MODERATOR.code);
            memberRepository.save(exAdmin);
        }
    }

}
