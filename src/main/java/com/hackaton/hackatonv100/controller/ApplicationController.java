package com.hackaton.hackatonv100.controller;


import com.hackaton.hackatonv100.facade.ApplicationFacade;
import com.hackaton.hackatonv100.model.enums.States;
import com.hackaton.hackatonv100.model.response.ApplicationResponse;
import com.hackaton.hackatonv100.service.IClanService;
import com.hackaton.hackatonv100.service.IMemberService;
import com.hackaton.hackatonv100.service.IApplicationService;
import com.hackaton.hackatonv100.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/api/application/")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Request In Clan Controller", description = "Контроллер для управлениями подачи заявок для поступления в клан")
public class ApplicationController {

    private IApplicationService applicationService;
    private IClanService clanService;
    private IMemberService memberService;
    private IUserService userService;
    private ApplicationFacade applicationFacade;

    @PostMapping("/clan/{clan_id}")
    @Operation(description = "Подать заявку на вступление в клан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Клан не найден"),
            @ApiResponse(responseCode = "406", description = "Пользователь уже состоит в клане")
    })
    public ResponseEntity<ApplicationResponse> requestInClan(
            Principal principal,
            @PathVariable("clan_id") Long id
    ) {
        if(!clanService.clanExists(id)) {
            return ResponseEntity.notFound().build();
        }

        var clan = clanService.getClan(id);
        var user = userService.getUser(principal);
        if(memberService.userInClan(user, clan)) {
            return ResponseEntity.status(406).build();
        }

        var request = applicationService.createRequestInClan(user, clan);
        var response = applicationFacade.applicationToApplicationResponse(request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/accept/{id}")
    @Operation(description = "Принять заявку на вступление в клан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена"),
            @ApiResponse(responseCode = "406", description = "Пользователь не может принять заявку")
    })
    public ResponseEntity<ApplicationResponse> accept(
            Principal principal,
            @PathVariable("id") Long applicationId
    ) {
        if(applicationService.applicationExists(applicationId)) {
            return ResponseEntity.notFound().build();
        }

        var application = applicationService.getApplication(applicationId);
        if(application.getState() != States.CREATED.code
                || !memberService.userInClan(userService.getUser(principal), application.getClan())) {
            return ResponseEntity.status(406).build();
        }

        application = applicationService.accept(application);
        var response = applicationFacade.applicationToApplicationResponse(application);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/cancel/{id}")
    @Operation(description = "Отклонить заявку на вступление в клан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Заявка не найдена"),
            @ApiResponse(responseCode = "406", description = "Пользователь не может отклонить заявку")
    })
    public ResponseEntity<ApplicationResponse> cancel(
            Principal principal,
            @PathVariable("id") Long applicationId
    ) {
        if(applicationService.applicationExists(applicationId)) {
            return ResponseEntity.notFound().build();
        }

        var application = applicationService.getApplication(applicationId);
        if(application.getState() != States.CREATED.code
                || !memberService.userInClan(userService.getUser(principal), application.getClan())) {
            return ResponseEntity.status(406).build();
        }

        application = applicationService.cancel(application);
        var response = applicationFacade.applicationToApplicationResponse(application);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/clan/{id}")
    @Operation(description = "Получить заявки в клан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Клан не найден")
    })
    public ResponseEntity<List<ApplicationResponse>> applicationsOfClan(@PathVariable("id") Long clanId) {
        if(clanService.clanExists(clanId)) {
            var applications = applicationService.getAllRequestsOfClan(clanId);
            var response = applicationFacade.applicationsToApplicationsResponse(applications);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.notFound().build();

        }
    }



    @GetMapping("/user")
    @Operation(description = "Получить заявки от пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<ApplicationResponse>> applicationsOfUser(Principal principal) {
        var applications = applicationService.getAllRequestsOfUser(principal);
        var response = applicationFacade.applicationsToApplicationsResponse(applications);
        return ResponseEntity.ok(response);
    }

}
