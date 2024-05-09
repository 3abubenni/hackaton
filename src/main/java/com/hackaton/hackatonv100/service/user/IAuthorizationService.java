package com.hackaton.hackatonv100.service.user;

import com.hackaton.hackatonv100.model.requests.LoginRequest;
import com.hackaton.hackatonv100.model.requests.RegisterRequest;
import org.springframework.stereotype.Service;

@Service
public interface IAuthorizationService {

    //Those methods return token as string
    String register(RegisterRequest request);
    String login(LoginRequest request);


}
