package com.hackaton.hackatonv100.model.enums.impl;

import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.UserUpdateRequest;
import com.hackaton.hackatonv100.repository.UserRepository;
import com.hackaton.hackatonv100.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@AllArgsConstructor
public class UserService implements IUserService
{

    private UserRepository userRepository;

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    @Override
    public User getUser(Principal principal) {
        return userRepository.findByEmail(principal.getName()).orElseThrow();
    }

    @Override
    public User updateUser(Principal principal, UserUpdateRequest request) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow();

        user.setBday(request.getBday());
        user.setFname(request.getFname());
        user.setLname(request.getLname());

        return userRepository.save(user);
    }

    @Override
    public boolean userExist(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean userExist(Long id) {
        return userRepository.existsById(id);
    }
}
