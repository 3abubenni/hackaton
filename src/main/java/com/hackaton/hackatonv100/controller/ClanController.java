package com.hackaton.hackatonv100.controller;

import com.hackaton.hackatonv100.facade.ClanFacade;
import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.requests.ClanRequest;
import com.hackaton.hackatonv100.model.response.ClanResponse;
import com.hackaton.hackatonv100.service.clan.IClanService;
import com.hackaton.hackatonv100.service.clan.IMemberService;
import com.hackaton.hackatonv100.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/api/clan")
@AllArgsConstructor
@Tag(name = "Clan Controller", description = "Контроллер для управления кланами")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClanController {

    private IClanService clanService;
    private IUserService userService;
    private IMemberService memberService;
    private ClanFacade clanFacade;

    @PostMapping
    @Operation(description = "Создать собственный клан. Пользователь может создать только один клан")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Клан успешно создан"),
            @ApiResponse(responseCode = "400", description = "Не валидный запрос: название клана " +
                    "либо меньше 3 сиволов либо больше 40 символов"),
            @ApiResponse(responseCode = "406", description = "Клан с таким названием уже существует"),
            @ApiResponse(responseCode = "420", description = "Пользователь уже имеет созданный клан")
    })
    public ResponseEntity<ClanResponse> createClan(
            @RequestBody @Valid ClanRequest request,
            BindingResult result,
            Principal principal
    ) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();

        } else if (clanService.clanWithThisNameExists(request.getName())) {
            return ResponseEntity.status(406).build();

        } else if (memberService.userHasClanAsAdmin(principal)) {
            return ResponseEntity.status(420).build();

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

        if (!clanService.clanExists(clanId)) {
            return ResponseEntity.notFound().build();

        } else if (result.hasErrors()) {
            return ResponseEntity.badRequest().build();

        } else if (clanService.userCanUpdateClan(principal, clanId)) {
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
        if (clanService.clanExists(clanId)) {
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
        if (userService.userExist(userID)) {
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

        } else if (clanService.deleteClan(principal, clanId)) {
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

        } else if (clanService.quitClan(principal, clanId)) {
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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/{id}/image")
    @Operation(description = "Загрузить изображение для клана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Неверный формат файла"),
            @ApiResponse(responseCode = "404", description = "Клан не найден"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может изменять клан")
    })
    public ResponseEntity<ClanResponse> uploadImage(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file,
            Principal principal
    ) {
        if (!clanService.clanExists(id)) {
            return ResponseEntity.notFound().build();

        } else if (!Objects.requireNonNull(file.getContentType()).startsWith("image")) {
            return ResponseEntity.badRequest().build();
        }

        var clan = clanService.getClan(id);
        if (memberService.userHaveStatusInClan(principal, clan, Member.MemberStatus.CAN_REDACT_CLAN)) {
            clan = clanService.uploadImage(file, clan);
            var response = clanFacade.clanToClanResponse(clan);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(403).build();
        }
    }


    @DeleteMapping("/{id}/image")
    @Operation(description = "Загрузить изображение для клана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Клан не найден"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может изменять клан")
    })
    public ResponseEntity<ClanResponse> deleteImage(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if (!clanService.clanExists(id)) {
            return ResponseEntity.notFound().build();

        }

        var clan = clanService.getClan(id);
        if (memberService.userHaveStatusInClan(principal, clan, Member.MemberStatus.CAN_REDACT_CLAN)) {
            clan = clanService.deleteImage(clan);
            var response = clanFacade.clanToClanResponse(clan);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(403).build();
        }
    }

}
