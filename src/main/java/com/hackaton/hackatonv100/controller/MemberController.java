package com.hackaton.hackatonv100.controller;

import com.hackaton.hackatonv100.facade.MemberFacade;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.requests.MemberRequest;
import com.hackaton.hackatonv100.model.response.MemberResponse;
import com.hackaton.hackatonv100.service.clan.IClanService;
import com.hackaton.hackatonv100.service.clan.IMemberService;
import com.hackaton.hackatonv100.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@Tag(name = "Member Controller", description = "Позволяет управлять участниками клана")
@AllArgsConstructor
@RequestMapping("/api/member")
@CrossOrigin(origins = "*", maxAge = 3600)
public class MemberController {

    private IMemberService memberService;
    private IUserService userService;
    private MemberFacade memberFacade;
    private IClanService clanService;

    @GetMapping("/user")
    @Operation(description = "Получить все записи о пользователе, как о участнике в кланах")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<MemberResponse>> getMembersOfUser(Principal principal) {
        List<Member> members = memberService.getMembersOfUser(principal);
        List<MemberResponse> response = memberFacade.membersToMembersResponse(members);
        return ResponseEntity.ok(response);

    }



    @GetMapping("/user/{id}")
    @Operation(description = "Получить все записи о пользователе по id, как о участнике в кланах")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<List<MemberResponse>> getMembersOfUser(@PathVariable("id") Long userId) {
        if(userService.userExist(userId)) {
            List<Member> members = memberService.getMembersOfUser(userId);
            List<MemberResponse> response = memberFacade.membersToMembersResponse(members);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.notFound().build();

        }
    }



    @GetMapping("/{id}")
    @Operation(description = "Получить информацию о участнике клана по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Участник клана не найден")
    })
    public ResponseEntity<MemberResponse> getMember(@PathVariable("id") Long memberId) {
        if(memberService.memberExists(memberId)) {
            Member member = memberService.getMember(memberId);
            MemberResponse response = memberFacade.memberToMemberResponse(member);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.notFound().build();

        }
    }

    @PutMapping("/{id}")
    @Operation(description = "Обновить информацию о участнике в клане")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Участник успешно обновлён"),
            @ApiResponse(responseCode = "404", description = "Участник не найден"),
            @ApiResponse(responseCode = "406", description = "Пользователь не может обновить участника"),
            @ApiResponse(responseCode = "400", description = "Количество валюты или опыта меньше 0")
    })
    public ResponseEntity<MemberResponse> updateMember(
            @PathVariable("id") Long id,
            @RequestBody MemberRequest request,
            Principal principal
    ) {

        if(request.getExp() < 0 || request.getMoney() < 0) {
            return ResponseEntity.badRequest().build();

        }
        else if(!memberService.memberExists(id)) {
            return ResponseEntity.notFound().build();

        } else {
            Member member = memberService.getMember(id);
            if(memberService.userHaveStatusInClan(
                    principal,
                    member.getClan(),
                    Member.MemberStatus.CAN_REDACT_MEMBER
            )) {
                member = memberService.updateMember(member, request);
                MemberResponse response = memberFacade.memberToMemberResponse(member);
                return ResponseEntity.ok(response);

            } else {
                return ResponseEntity.status(406).build();

            }
        }
    }



    @PutMapping("/change_status/{id}")
    @Operation(description = "Изменить статус участника. В клане не может быть больше одного админа, поэтому если " +
            "какой-либо участник получает статус админа, действующий админ получает статус модератора. Система статусов " +
            "(прав) работает на основе битовых флагов (степеней 2). Далее список кодов " +
            "со статусами: \n" +
            "1) Участник \n" +
            "2) Может проверсять задачи \n" +
            "4) Может создавать задачи \n" +
            "8) Может приглашать и принимать приглашения \n" +
            "16) Может редактировать клан \n" +
            "32) Может удалить клан \n" +
            "64) Может исключать \n" +
            "128) Может редактировать участников \n" +
            "256) Может менять статус участников \n"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Статус участника успешно обновлён"),
            @ApiResponse(responseCode = "404", description = "Участник не найден"),
            @ApiResponse(responseCode = "406", description = "Пользователь не может обновить статус участника, " +
                    "или админ пытается сам себе поменять статус"),
            @ApiResponse(responseCode = "400", description = "Не валидный статус")
    })
    public ResponseEntity<MemberResponse> changeStatus(
            @PathVariable("id") Long memberId,
            @Param("status") int status,
            Principal principal
    ) {
        if(status > Member.MemberStatus.ADMIN.code || status < 0) {
            return ResponseEntity.badRequest().build();

        } else if(!memberService.memberExists(memberId)) {
            return ResponseEntity.notFound().build();

        } else {
            Member member = memberService.getMember(memberId);
            if(!member.checkStatus(Member.MemberStatus.ADMIN) &&
                    memberService.userHaveStatusInClan(
                    principal,
                    member.getClan(),
                    Member.MemberStatus.CAN_CHANGE_STATUS
            )) {
                member = memberService.changeMemberStatus(member, status);
                MemberResponse response = memberFacade.memberToMemberResponse(member);
                return ResponseEntity.ok(response);
            }
            return ResponseEntity.status(406).build();
        }
    }


    @DeleteMapping("/{id}")
    @Operation(description = "Удалить (выгнать) участника")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Участник выгнан"),
            @ApiResponse(responseCode = "404", description = "Участник не найден"),
            @ApiResponse(responseCode = "406", description = "Участинк не может выгонять других участников " +
                    "(самого себя тоже не выгонишь)")
    })
    public void kickOutMember(
            @PathVariable("id") Long memberId,
            Principal principal,
            HttpServletResponse response
    ) {
        if(!memberService.memberExists(memberId)) {
            response.setStatus(404);
        } else if(memberService.kickOutMember(principal, memberId)) {
            response.setStatus(200);

        } else {
            response.setStatus(406);
        }
    }



    @GetMapping("/user/clan/{id}")
    @Operation(description = "Даёт информацию о пользователе как о участнике в конкретном клане")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Клан не найден"),
            @ApiResponse(responseCode = "406", description = "Пользователь не состоит в клане")
        }
    )
    public ResponseEntity<MemberResponse> userInClan(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if(!clanService.clanExists(id)) {
            return ResponseEntity.notFound().build();
        }

        if(!memberService.userInClan(principal, id)) {
            return ResponseEntity.status(406).build();
        }

        var member = memberService.getMember(principal, id);
        var response = memberFacade.memberToMemberResponse(member);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/clan/{id}")
    @Operation(description = "Получить всех участников клана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Клан не найден")
    }
    )
    public ResponseEntity<List<MemberResponse>> membersOfClan(@PathVariable("id") Long clanId) {
        if (!clanService.clanExists(clanId)) {
            return ResponseEntity.notFound().build();
        } else {
            var members = memberService.membersOfClan(clanId);
            var response = memberFacade.membersToMembersResponse(members);
            return ResponseEntity.ok(response);
        }
    }

}
