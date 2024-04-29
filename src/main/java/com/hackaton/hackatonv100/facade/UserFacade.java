package com.hackaton.hackatonv100.facade;

import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.response.UserResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserFacade {

    public UserResponse userToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .bday(user.getBday())
                .email(user.getEmail())
                .fname(user.getFname())
                .lname(user.getLname())
                .build();
    }

    public List<UserResponse> usersToUsersResponse(Collection<User> users) {
        return users.stream()
                .map(this::userToUserResponse)
                .collect(Collectors.toList());
    }



}
