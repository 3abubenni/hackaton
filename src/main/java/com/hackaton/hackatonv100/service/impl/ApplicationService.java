package com.hackaton.hackatonv100.service.impl;

import com.hackaton.hackatonv100.model.ApplicationModel;
import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.enums.States;
import com.hackaton.hackatonv100.repository.ApplicationRepository;
import com.hackaton.hackatonv100.service.IApplicationService;
import com.hackaton.hackatonv100.service.IClanService;
import com.hackaton.hackatonv100.service.IMemberService;
import com.hackaton.hackatonv100.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@AllArgsConstructor
public class ApplicationService implements IApplicationService {

    private ApplicationRepository applicationRepository;
    private IUserService userService;
    private IClanService clanService;
    private IMemberService memberService;

    @Override
    public ApplicationModel createRequestInClan(User user, Clan clan) {
        applicationRepository.deleteAllByClan(clan);
        var application = ApplicationModel.builder()
                .user(user)
                .clan(clan)
                .state(States.CREATED.code)
                .build();

        return applicationRepository.save(application);
    }

    @Override
    public ApplicationModel cancel(ApplicationModel application) {
        application.setState(States.CANCELED);
        return applicationRepository.save(application);
    }

    @Override
    public ApplicationModel accept(ApplicationModel application) {
        memberService.createMember(
                application.getClan(),
                application.getUser(),
                Member.MemberStatus.MEMBER
        );

        application.setState(States.ACCEPTED);
        return applicationRepository.save(application);
    }

    @Override
    public void delete(Long requestId) {
        applicationRepository.deleteById(requestId);
    }

    @Override
    public ApplicationModel getApplication(Long requestId) {
        return applicationRepository.findById(requestId).orElseThrow();
    }

    @Override
    public boolean applicationExists(Long requestId) {
        return applicationRepository.existsById(requestId);
    }

    @Override
    public List<ApplicationModel> getAllRequestsOfUser(Principal principal) {
        return applicationRepository.findAllByUser(userService.getUser(principal));
    }

    @Override
    public List<ApplicationModel> getAllRequestsOfClan(Long clanId) {
        return applicationRepository.findAllByClan(clanService.getClan(clanId));
    }
}
