package com.hackaton.hackatonv100.service.impl;

import com.hackaton.hackatonv100.model.Application;
import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.enums.States;
import com.hackaton.hackatonv100.repository.ApplicationRepository;
import com.hackaton.hackatonv100.service.IApplicationService;
import com.hackaton.hackatonv100.service.IClanService;
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

    @Override
    public Application createRequestInClan(User user, Clan clan) {
        applicationRepository.deleteAllByClan(clan);
        var application = Application.builder()
                .user(user)
                .clan(clan)
                .state(States.CREATED.code)
                .build();

        return applicationRepository.save(application);
    }

    @Override
    public Application cancel(Application application) {
        application.setState(States.CANCELED);
        return applicationRepository.save(application);
    }

    @Override
    public Application accept(Application application) {
        application.setState(States.ACCEPTED);
        return applicationRepository.save(application);
    }

    @Override
    public void delete(Long requestId) {
        applicationRepository.deleteById(requestId);
    }

    @Override
    public Application getApplication(Long requestId) {
        return applicationRepository.findById(requestId).orElseThrow();
    }

    @Override
    public boolean applicationExists(Long requestId) {
        return applicationRepository.existsById(requestId);
    }

    @Override
    public List<Application> getAllRequestsOfUser(Principal principal) {
        return applicationRepository.findAllByUser(userService.getUser(principal));
    }

    @Override
    public List<Application> getAllRequestsOfClan(Long clanId) {
        return applicationRepository.findAllByClan(clanService.getClan(clanId));
    }
}
