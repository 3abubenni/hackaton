package com.hackaton.hackatonv100.service.clan.impl;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Task;
import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.ClanRequest;
import com.hackaton.hackatonv100.repository.ClanRepository;
import com.hackaton.hackatonv100.service.clan.*;
import com.hackaton.hackatonv100.service.user.IUserService;
import com.hackaton.hackatonv100.service.utilservice.IImageService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClanService implements IClanService {

    private ClanRepository clanRepository;
    private IMemberService memberService;
    private IUserService userService;
    private IApplicationService applicationService;
    private IInviteService inviteService;
    private ITaskService taskService;
    private IImageService imageService;

    @PostConstruct
    public void init() {
        memberService.setClanService(this);
        applicationService.setClanService(this);
        inviteService.setClanService(this);
        taskService.setClan(this);
    }


    @Override
    public Clan createClan(Principal principal, ClanRequest request) {
        return createClan(userService.getUser(principal), request);
    }

    @Override
    public Clan updateClan(ClanRequest request, Long clanId) {
        var clan = clanRepository.findById(clanId).orElseThrow();
        clan.setName(request.getName());
        clan.setDescription(request.getDescription());
        return clanRepository.save(clan);
    }

    @Override
    public Clan createClan(User user, ClanRequest request) {
        var clan = Clan.builder()
                .name(request.getName().strip())
                .description(request.getDescription().strip())
                .build();
        clan = clanRepository.save(clan);
        memberService.createMember(clan, user, Member.MemberStatus.ADMIN);
        return clanRepository.save(clan);
    }

    @Override
    public boolean deleteClan(Principal principal, Long clanId) {
        if (memberService.userInClan(principal, clanId)) {
            Member member = memberService.getMember(principal, clanId);
            if (member.checkStatus(Member.MemberStatus.CAN_DELETE_CLAN)) {
                deleteClan(clanId);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean quitClan(Principal principal, Long clanId) {
        if (memberService.userInClan(principal, clanId)) {
            var clan = clanRepository.findById(clanId).orElseThrow();
            var user = userService.getUser(principal);
            var member = memberService.getMember(user, clan);
            if (member.checkStatus(Member.MemberStatus.ADMIN)) {
                deleteClan(clanId);

            } else {
                var tasks = taskService.getTasksOfClan(clan).stream()
                        .peek(t -> {
                            t.setSolver(null);
                            if (t.getStatus() == Task.SolutionStatus.TOOK.num
                                    || t.getStatus() == Task.SolutionStatus.SOLVED.num) {
                                t.setStatus(Task.SolutionStatus.CREATED);
                            }
                        })
                        .collect(Collectors.toList());

                taskService.saveAllTasks(tasks);
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
        var user = userService.getUser(userId);
        return getClansOfUser(user);
    }

    @Override
    public List<Clan> getClansOfUser(Principal principal) {
        var user = userService.getUser(principal);
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
        query = query.toLowerCase(Locale.ROOT) + '%';
        return clanRepository.searchByName(query, 15L);
    }

    @Override
    public boolean clanExists(Long clanId) {
        return clanRepository.existsById(clanId);
    }

    @Override
    public boolean userCanUpdateClan(Principal principal, Long clanId) {
        var member = memberService.getMember(principal, clanId);
        return member.checkStatus(Member.MemberStatus.CAN_REDACT_CLAN);
    }

    @Override
    public boolean clanWithThisNameExists(String name) {
        return clanRepository.existsByName(name);
    }

    @Override
    public Clan uploadImage(MultipartFile multipartFile, Clan clan) {
        try {
            var image = imageService.uploadImage(multipartFile);
            deleteImage(clan);
            clan.setImg(image.getName());
            return clanRepository.save(clan);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public Clan deleteImage(Clan clan) {
        if(clan.getImg() != null) {
            imageService.deleteImg(clan.getImg());
            clan.setImg(null);
        }
        return clanRepository.save(clan);
    }

    private void deleteClan(long id) {
        var clan = clanRepository.findById(id).orElseThrow();
        List<String> names = clan.getShop().stream()
                .map(i -> i.getItem().getImg())
                .toList();

        imageService.deleteImgs(names);
        deleteImage(clan);
        clanRepository.deleteById(id);
    }

}
