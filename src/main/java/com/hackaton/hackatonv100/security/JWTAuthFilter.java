package com.hackaton.hackatonv100.security;

import com.hackaton.hackatonv100.service.JWTService;
import com.hackaton.hackatonv100.service.user.impl.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Collections;


@Component
@AllArgsConstructor
public class JWTAuthFilter extends OncePerRequestFilter {

    private JWTService jwtService;
    private UserService userService;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain

    ) throws ServletException, IOException {

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String email = jwtService.extractSubject(token);

        if(email != null && userService.userExist(email)) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    email, null, Collections.emptyList()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);

    }
}
