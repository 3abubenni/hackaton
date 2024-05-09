package com.hackaton.hackatonv100.controller;

import com.hackaton.hackatonv100.facade.UserFacade;
import com.hackaton.hackatonv100.model.User;
import com.hackaton.hackatonv100.model.requests.LoginRequest;
import com.hackaton.hackatonv100.model.requests.RegisterRequest;
import com.hackaton.hackatonv100.model.requests.UserUpdateRequest;
import com.hackaton.hackatonv100.model.response.UserResponse;
import com.hackaton.hackatonv100.service.user.IAuthorizationService;
import com.hackaton.hackatonv100.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@Tag(name = "User Controller", description = "Контроллер для управления данными пользователей")
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private IUserService userService;
    private IAuthorizationService authorizationService;
    private UserFacade userFacade;



    @PostMapping("/register")
    @Operation(description = "Регистрация нового пользователя. \n" +
            "Токен для аутентификации при успешной регистрации находится в header Authorization" +
            "Данные для регистрации должны удовлетворять следующим требованиям: \n" +
            "1) Пароль не меньше 8 символов\n" +
            "2) Имя и фамилия пользователся не должны быть пустыми и должны быть не меньше 2 символов\n" +
            "3) Дата рождения не может быть похже текущей даты\n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно зарегестрирован"),
            @ApiResponse(responseCode = "406", description = "Пользователь с таким email уже существует"),
            @ApiResponse(responseCode = "400", description = "Данные не прошли валидацию")
    })
    public ResponseEntity<String> register(
            @RequestBody @Valid RegisterRequest request,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();

        } else if(userService.getUser(request.getEmail()) != null) {
            return ResponseEntity.status(406).build();

        } else {
            String token = authorizationService.register(request);
            return ResponseEntity.ok(token);
        }
    }



    @PostMapping("/login")
    @Operation(description = "Авторизация пользователя\n" +
            "Токен для аутентификации лежит в header Authorization")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно авторизовался"),
            @ApiResponse(responseCode = "401", description = "Неверный email или пароль")
    })
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String token = authorizationService.login(request);
        return ResponseEntity.ok(token);
    }



    @GetMapping
    @Operation(description = "Получение информации об авторизованном пользователе")
    @ApiResponse(responseCode = "200", description = "Информация о авторизованном пользователе")
    public ResponseEntity<UserResponse> user(Principal principal) {
        User user = userService.getUser(principal);
        UserResponse response = userFacade.userToUserResponse(user);
        return ResponseEntity.ok(response);

    }


    @GetMapping("/{id}")
    @Operation(description = "Получение информации о пользователе по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<UserResponse> user(@PathVariable("id") Long id) {
        if(userService.userExist(id)) {
            User user = userService.getUser(id);
            UserResponse response = userFacade.userToUserResponse(user);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.notFound().build();

        }
    }



    @PutMapping
    @Operation(description = "Обновление данных о пользователе")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные успешно обновлены"),
            @ApiResponse(responseCode = "400", description = "Данные не прошли валидацию: \n" +
                    "1) Имя или фамилия содержат меньше 2 символов\n" +
                    "2) Дата рождения после текущей даты")
    })
    public ResponseEntity<UserResponse> updateUser(
            Principal principal,
            @RequestBody @Valid UserUpdateRequest request,
            BindingResult bindingResult
    ) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();

        } else {
            User user = userService.updateUser(principal, request);
            UserResponse response = userFacade.userToUserResponse(user);
            return ResponseEntity.ok(response);

        }
    }


    @GetMapping("/search")
    @Operation(description = "Искать пользователей по имени или email")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<UserResponse>> searchUsers(@Param("query") String query) {
        var users = userService.searchUsers(query);
        var response = userFacade.usersToUsersResponse(users);
        return ResponseEntity.ok(response);
    }

}
