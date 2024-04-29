package com.hackaton.hackatonv100.service;

import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.LoginRequest;
import com.hackaton.hackatonv100.model.requests.RegisterRequest;
import com.hackaton.hackatonv100.model.requests.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface IAuthorizationService {

    //Those methods return token as string
    String register(RegisterRequest request);
    String login(LoginRequest request);


}
