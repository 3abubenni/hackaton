package com.hackaton.hackatonv100.facade;

import com.hackaton.hackatonv100.model.ApplicationModel;
import com.hackaton.hackatonv100.model.response.ApplicationResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplicationFacade {

    public ApplicationResponse applicationToApplicationResponse(ApplicationModel request) {
        return ApplicationResponse.builder()
                .clanId(request.getClan().getId())
                .id(request.getId())
                .state(request.getState())
                .userId(request.getUser().getId())
                .build();
    }

    public List<ApplicationResponse> applicationsToApplicationsResponse(Collection<ApplicationModel> requests) {
        return requests.stream()
                .map(this::applicationToApplicationResponse)
                .collect(Collectors.toList());
    }

}
