package com.hackaton.hackatonv100.facade;

import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.response.MemberResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberFacade {

    public MemberResponse memberToMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .solvedTasks(member.getSolvedTasks())
                .exp(member.getExp())
                .items(member.getCountItems())
                .clanId(member.getClan().getId())
                .userId(member.getUser().getId())
                .money(member.getMoney())
                .status(member.getStatus())
                .build();
    }

    public List<MemberResponse> membersToMembersResponse(Collection<Member> members) {
        return members.stream()
                .map(this::memberToMemberResponse)
                .collect(Collectors.toList());
    }

}
