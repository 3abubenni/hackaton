package com.hackaton.hackatonv100.service.user;

import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.UserUpdateRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Service
public interface IUserService {

    User getUser(String email);
    User getUser(Long id);
    User getUser(Principal principal);
    User updateUser(Principal principal, UserUpdateRequest request);
    boolean userExist(String email);
    boolean userExist(Long id);
    List<User> searchUsers(String query);
    User uploadImgToUser(Principal principal, MultipartFile multipartFile);
    User deleteImgOfUser(Principal principal);

}
