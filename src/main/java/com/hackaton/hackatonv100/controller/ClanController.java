package com.hackaton.hackatonv100.controller;

import com.hackaton.hackatonv100.facade.ClanFacade;
import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.requests.ClanRequest;
import com.hackaton.hackatonv100.model.response.ClanResponse;
import com.hackaton.hackatonv100.model.response.MemberResponse;
import com.hackaton.hackatonv100.service.IClanService;
import com.hackaton.hackatonv100.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/clan")
@AllArgsConstructor
@Tag(name = "Clan Controller", description = "Контроллер для управления кланами")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClanController {

    private IClanService clanService;
    private IUserService userService;
    private ClanFacade clanFacade;

    @PostMapping
    @Operation(description = "Создать собственный клан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клан успешно создан"),
            @ApiResponse(responseCode = "400", description = "Не валидный запрос: название клана " +
                    "либо меньше 3 сиволов либо больше 40 символов")
    })
    public ResponseEntity<ClanResponse> createClan(
            @RequestBody @Valid ClanRequest request,
            BindingResult result,
            Principal principal
    ) {
        if(result.hasErrors()) {
            return ResponseEntity.badRequest().build();

        } else {
            Clan clan = clanService.createClan(principal, request);
            ClanResponse response = clanFacade.clanToClanResponse(clan);
            return ResponseEntity.ok(response);

        }
    }



    @PutMapping("/{id}")
    @Operation(description = "Обновить клан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клан успешно обновлён"),
            @ApiResponse(responseCode = "400", description = "Не валидный запрос"),
            @ApiResponse(responseCode = "406", description = "Пользователь не может редактировать клан"),
            @ApiResponse(responseCode = "404", description = "Клан не найден")
    })
    public ResponseEntity<ClanResponse> updateClan(
            @RequestBody @Valid ClanRequest request,
            BindingResult result,
            Principal principal,
            @PathVariable("id") Long clanId
    ) {

       if(!clanService.clanExists(clanId)) {
           return ResponseEntity.notFound().build();

       } else if (result.hasErrors()) {
           return ResponseEntity.badRequest().build();

       } else if(clanService.userCanUpdateClan(principal, clanId)) {
           Clan clan = clanService.updateClan(request, clanId);
           ClanResponse response = clanFacade.clanToClanResponse(clan);
           return ResponseEntity.ok(response);

       } else {
           return ResponseEntity.status(406).build();

       }
    }



    @GetMapping("/{id}")
    @Operation(description = "Получить информацию о клане")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ОК, даже сказать нечего"),
            @ApiResponse(responseCode = "404", description = "Not Found")
    })
    public ResponseEntity<ClanResponse> getClan(@PathVariable("id") Long clanId) {
        if(clanService.clanExists(clanId)) {
            Clan clan = clanService.getClan(clanId);
            ClanResponse response = clanFacade.clanToClanResponse(clan);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.notFound().build();

        }
    }



    @GetMapping("/user/{id}")
    @Operation(description = "Получить информацию о кланах пользователя по id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<List<ClanResponse>> getClanOfUser(@PathVariable("id") Long userID) {
        if(userService.userExist(userID)) {
            List<Clan> clans = clanService.getClansOfUser(userID);
            List<ClanResponse> response = clanFacade.clansToClanResponses(clans);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.notFound().build();

        }
    }



    @GetMapping("/user")
    @Operation(description = "Получить информацию о кланах пользователя")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<ClanResponse>> getClanOfUser(Principal principal) {
        List<Clan> clans = clanService.getClansOfUser(principal);
        List<ClanResponse> response = clanFacade.clansToClanResponses(clans);
        return ResponseEntity.ok(response);

    }



    @DeleteMapping("/{id}")
    @Operation(description = "Удалить клан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клан удалён"),
            @ApiResponse(responseCode = "404", description = "Клан не найден"),
            @ApiResponse(responseCode = "406", description = "Пользователь не может удалить клан")
    })
    public void deleteClan(
            @PathVariable("id") Long clanId,
            HttpServletResponse response,
            Principal principal
    ) {
        if (!clanService.clanExists(clanId)) {
            response.setStatus(404);

        } else if(clanService.deleteClan(principal, clanId)) {
            response.setStatus(200);

        } else {
            response.setStatus(406);

        }
    }


    @DeleteMapping("/quit/{id}")
    @Operation(description = "Покинуть клан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь вышел из клана"),
            @ApiResponse(responseCode = "404", description = "Клан не найден"),
            @ApiResponse(responseCode = "406", description = "Пользователь не учавствует в клане")
    })
    public void quitClan(
            @PathVariable("id") Long clanId,
            HttpServletResponse response,
            Principal principal
    ) {
        if (!clanService.clanExists(clanId)) {
            response.setStatus(404);

        } else if(clanService.quitClan(principal, clanId)) {
            response.setStatus(200);

        } else {
            response.setStatus(406);

        }
    }



    @GetMapping("/search")
    @Operation(description = "поиск кланов")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<ClanResponse>> searchClans(@RequestParam String query) {
        List<Clan> clans = clanService.searchClan(query);
        List<ClanResponse> response = clanFacade.clansToClanResponses(clans);
        return ResponseEntity.ok(response);

    }


}
