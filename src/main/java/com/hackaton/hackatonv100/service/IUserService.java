package com.hackaton.hackatonv100.service;

import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.UserUpdateRequest;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public interface IUserService {

    User getUser(String email);
    User getUser(Long id);
    User getUser(Principal principal);
    User updateUser(Principal principal, UserUpdateRequest request);
    boolean userExist(String email);
    boolean userExist(Long id);

}
