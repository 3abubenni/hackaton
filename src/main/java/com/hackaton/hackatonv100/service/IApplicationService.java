package com.hackaton.hackatonv100.service;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.ApplicationModel;
import com.hackaton.hackatonv100.model.User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface IApplicationService {

    ApplicationModel createRequestInClan(User user, Clan clan);
    ApplicationModel cancel(ApplicationModel request);
    ApplicationModel accept(ApplicationModel request);
    void delete(Long requestId);
    ApplicationModel getApplication(Long requestId);
    boolean applicationExists(Long requestId);
    List<ApplicationModel> getAllRequestsOfUser(Principal principal);
    List<ApplicationModel> getAllRequestsOfClan(Long clanId);

}
