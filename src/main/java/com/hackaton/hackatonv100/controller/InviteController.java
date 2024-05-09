package com.hackaton.hackatonv100.controller;


import com.hackaton.hackatonv100.facade.InviteFacade;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.enums.States;
import com.hackaton.hackatonv100.model.response.InviteResponse;
import com.hackaton.hackatonv100.service.clan.IClanService;
import com.hackaton.hackatonv100.service.clan.IInviteService;
import com.hackaton.hackatonv100.service.clan.IMemberService;
import com.hackaton.hackatonv100.service.user.IUserService;
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
@RequestMapping("/api/invite")
@Tag(name = "Invite Controller", description = "Контроллер для управления приглашениями")
@CrossOrigin(origins = "*", maxAge = 3600)
public class InviteController {

    private IInviteService inviteService;
    private IUserService userService;
    private InviteFacade inviteFacade;
    private IClanService clanService;
    private IMemberService memberService;



    @PostMapping("/clan/{clan_id}/user/{user_id}")
    @Operation(description = "Пригласить пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Пользователь или клан не найден"),
            @ApiResponse(responseCode = "406", description = "Пользователь уже состоит в клане, или " +
                    "пользователь (создатель приглашения) не может приглашать пользователей")
    })
    public ResponseEntity<InviteResponse> invite(
            @PathVariable("user_id") Long userId,
            @PathVariable("clan_id") Long clanId,
            Principal principal
    ) {
        if (!(userService.userExist(userId) && clanService.clanExists(clanId))) {
            return ResponseEntity.notFound().build();
        }

        var inventor = userService.getUser(principal);
        var invented = userService.getUser(userId);
        var clan = clanService.getClan(clanId);
        if(!memberService.userInClan(inventor, clan) || memberService.userInClan(invented, clan)) {
            return ResponseEntity.status(406).build();

        } else {
            var member = memberService.getMember(inventor, clan);
            if(member.checkStatus(Member.MemberStatus.CAN_INVITE_MEMBER)) {
                var invite = inviteService.inviteUser(invented, clan);
                var response = inviteFacade.inviteToInviteResponse(invite);
                return ResponseEntity.ok(response);

            } else {
                return ResponseEntity.status(406).build();
            }
        }
    }



    @PostMapping("/accept/{id}")
    @Operation(description = "Принять приглашение в клан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Приглашение не найдено"),
            @ApiResponse(responseCode = "406", description = "Приглашение неактивно или не относится к пользователю")
    })
    public ResponseEntity<InviteResponse> acceptInvite(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if(inviteService.inviteExists(id)) {
            var invite = inviteService.getInvite(id);
            var user = userService.getUser(principal);
            if(invite.getState() == States.CREATED.code && invite.getUser().equals(user)) {
                invite = inviteService.acceptInvite(invite);
                var response = inviteFacade.inviteToInviteResponse(invite);
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(406).build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @PostMapping("/cancel/{id}")
    @Operation(description = "Отменить приглашение")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Приглашение не найдено"),
            @ApiResponse(responseCode = "406", description = "Приглашение не относиться к пользователю или клану или не активно")
    })
    public ResponseEntity<InviteResponse> cancelInvite(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if(inviteService.inviteExists(id)) {
            var invite = inviteService.getInvite(id);
            var user = userService.getUser(principal);
            if(invite.getState() == States.CREATED.code && invite.getUser().equals(user)) {
                invite = inviteService.cancelInvite(invite);
                var response = inviteFacade.inviteToInviteResponse(invite);
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(406).build();

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/user")
    @Operation(description = "Получить все приглашения пользователя")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<InviteResponse>> invitesOfUser(Principal principal) {
        var invites = inviteService.invitesOfUser(principal);
        var response = inviteFacade.invitesToInvitesResponse(invites);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/clan/{id}")
    @Operation(description = "Получить приглашения от клана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Клан не найден"),
            @ApiResponse(responseCode = "406", description = "Пользователь не может приглашать пользователей в клан")
    })
    public ResponseEntity<List<InviteResponse>> invitesOfClan(
            @PathVariable("id") Long clanId,
            Principal principal

    ) {
        if (!clanService.clanExists(clanId)) {
            return ResponseEntity.notFound().build();
        }

        if(memberService.userHaveStatusInClan(principal, clanId, Member.MemberStatus.CAN_INVITE_MEMBER)) {
            var invites = inviteService.invitesOfClan(clanId);
            var response = inviteFacade.invitesToInvitesResponse(invites);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(406).build();
        }
    }




}
