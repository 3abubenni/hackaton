package com.hackaton.hackatonv100.service.user.impl;

import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.UserUpdateRequest;
import com.hackaton.hackatonv100.repository.UserRepository;
import com.hackaton.hackatonv100.service.user.IUserService;
import com.hackaton.hackatonv100.service.utilservice.IImageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private UserRepository userRepository;
    private IImageService imageService;

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
        var user = userRepository.findByEmail(principal.getName()).orElseThrow();
        user.setBday(request.getBday());
        user.setFname(request.getFname().strip());
        user.setLname(request.getLname().strip());
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

    @Override
    public List<User> searchUsers(String query) {
        query = query.toLowerCase(Locale.ROOT) + '%';
        return userRepository.searchUser(query, 15L);
    }

    @Override
    public User uploadImgToUser(Principal principal, MultipartFile file) {
        try {
            var img = imageService.uploadImage(file);
            var user = getUser(principal);
            imageService.deleteImg(user.getImg());
            user.setImg(img.getName());
            return userRepository.save(user);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public User deleteImgOfUser(Principal principal) {
        var user = getUser(principal);
        if(user.getImg() != null) {
            imageService.deleteImg(user.getImg());
            user.setImg(null);
            return userRepository.save(user);
        }
        return userRepository.save(user);
    }
}
