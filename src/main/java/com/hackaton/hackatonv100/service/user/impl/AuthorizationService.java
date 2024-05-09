package com.hackaton.hackatonv100.service.user.impl;

import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.LoginRequest;
import com.hackaton.hackatonv100.model.requests.RegisterRequest;
import com.hackaton.hackatonv100.repository.UserRepository;
import com.hackaton.hackatonv100.service.user.IAuthorizationService;
import com.hackaton.hackatonv100.service.JWTService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j
public class AuthorizationService implements IAuthorizationService {

    private BCryptPasswordEncoder encoder;
    private UserRepository userRepository;
    private JWTService jwtService;
    private AuthenticationManager authenticationManager;

    @Override
    public String register(RegisterRequest request) {
        log.info("Created user by email: " + request.getEmail() + ", password: "  + request.getPassword());
        User user = User.builder()
                .bday(request.getBday())
                .fname(request.getFname())
                .lname(request.getLname())
                .email(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .build();

        userRepository.save(user);
        return jwtService.generateToken(user.getEmail());
    }

    @Override
    public String login(LoginRequest request) {
        log.info("Authorization User: " + request.getEmail() + " by password: " + request.getPassword());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()
        );

        authenticationManager.authenticate(token);
        return jwtService.generateToken(request.getEmail());
    }



}
