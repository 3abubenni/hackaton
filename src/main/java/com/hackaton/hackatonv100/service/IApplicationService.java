package com.hackaton.hackatonv100.service;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Application;
import com.hackaton.hackatonv100.model.User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface IApplicationService {

    Application createRequestInClan(User user, Clan clan);
    Application cancel(Application request);
    Application accept(Application request);
    void delete(Long requestId);
    Application getApplication(Long requestId);
    boolean applicationExists(Long requestId);
    List<Application> getAllRequestsOfUser(Principal principal);
    List<Application> getAllRequestsOfClan(Long clanId);

}
