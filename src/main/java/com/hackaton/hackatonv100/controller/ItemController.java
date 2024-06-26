package com.hackaton.hackatonv100.controller;


import com.hackaton.hackatonv100.facade.ItemFacade;
import com.hackaton.hackatonv100.facade.ItemTransferFacade;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.requests.ItemRequest;
import com.hackaton.hackatonv100.model.response.ItemDetailsResponse;
import com.hackaton.hackatonv100.model.response.ItemResponse;
import com.hackaton.hackatonv100.model.response.ItemTransferResponse;
import com.hackaton.hackatonv100.service.clan.IClanService;
import com.hackaton.hackatonv100.service.clan.IItemService;
import com.hackaton.hackatonv100.service.clan.IMemberService;
import com.hackaton.hackatonv100.service.operation.IItemTransferService;
import com.hackaton.hackatonv100.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
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
@AllArgsConstructor
@Tag(name = "Item Controller", description = "Контроллер для управления предметами клана")
@RequestMapping("/api/item")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ItemController {


    private IItemService itemService;
    private IUserService userService;
    private IClanService clanService;
    private IMemberService memberService;
    private IItemTransferService iItemTransferService;
    private ItemFacade itemFacade;
    private ItemTransferFacade itemTransferFacade;


    @PostMapping("/clan/{id}")
    @Operation(description = "Создать предмет для клана. Длина имени предмета должна быть не меньше 3 символов" +
            " и не больше 40, а также стоимость и количество должны быть не меньше 0")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Запрос не валиден."),
            @ApiResponse(responseCode = "404", description = "Клан не найден"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может создавать предметы для клана"),
            @ApiResponse(responseCode = "406", description = "Предмет с таким именем уже существует в клане")
    })
    public ResponseEntity<ItemResponse> createItem(
            @RequestBody @Valid ItemRequest request,
            BindingResult bindingResult,
            Principal principal,
            @PathVariable("id") Long id
    ) {
        if(!clanService.clanExists(id)) {
            return ResponseEntity.notFound().build();

        } else if(bindingResult.hasErrors() || !request.isValid()) {
            return ResponseEntity.badRequest().build();

        } else if(itemService.itemWithThisNameExistsInClan(request.getName(), id)) {
            return ResponseEntity.status(406).build();
        }

        var clan = clanService.getClan(id);
        var user = userService.getUser(principal);
        if(memberService.userInClan(user, clan)) {
            var member = memberService.getMember(user, clan);
            if(member.checkStatus(Member.MemberStatus.CAN_MANAGE_ITEMS)) {
                var item = itemService.createItem(request, clan);
                var response = itemFacade.itemToItemResponse(item);
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(403).build();
    }


    @PutMapping("/{id}")
    @Operation(description = "Обновить предмет. Длина имени предмета должна быть не меньше 3 символов" +
            " и не больше 40, а также стоимость и количество должны быть не меньше 0")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Запрос не валиден."),
            @ApiResponse(responseCode = "404", description = "Предмет не найден"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может управлять предметами клана"),
            @ApiResponse(responseCode = "406", description = "Предмет с таким именем уже существует в клане")
    })
    public ResponseEntity<ItemResponse> updateItem(
            @RequestBody @Valid ItemRequest request,
            BindingResult bindingResult,
            Principal principal,
            @PathVariable("id") Long id
    ) {
        if(!itemService.itemExist(id)) {
            return ResponseEntity.notFound().build();

        } else if(bindingResult.hasErrors() || !request.isValid()) {
            return ResponseEntity.badRequest().build();

        }

        var item = itemService.getItem(id);

        if(!item.getName().equals(request.getName())
                && itemService.itemWithThisNameExistsInClan(request.getName(), item.getClan().getId())) {
            return ResponseEntity.status(406).build();
        }

        var user = userService.getUser(principal);
        if(memberService.userInClan(user, item.getClan())) {
            var member = memberService.getMember(user, item.getClan());
            if(member.checkStatus(Member.MemberStatus.CAN_MANAGE_ITEMS)) {
                item = itemService.updateItem(request, id);
                var response = itemFacade.itemToItemResponse(item);
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(403).build();
    }



    @DeleteMapping("/{id}")
    @Operation(description = "Удалить предмет")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Предмет не найден"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может управлять предметами клана"),
    })
    public void deleteItem(
            Principal principal,
            @PathVariable("id") Long id,
            HttpServletResponse response
    ) {
        if(!itemService.itemExist(id)) {
            response.setStatus(404);
            return;
        }

        var item = itemService.getItem(id);
        var user = userService.getUser(principal);
        if(memberService.userInClan(user, item.getClan())) {
            var member = memberService.getMember(user, item.getClan());
            if(member.checkStatus(Member.MemberStatus.CAN_MANAGE_ITEMS)) {
                itemService.deleteItem(id);
                response.setStatus(200);
                return;
            }
        }
        response.setStatus(403);
    }


    @GetMapping("/member/{id}")
    @Operation(description = "Получить предметы участника")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Участник не найден"),
            @ApiResponse(responseCode = "403", description = "Пользователь не состоит в одном клане с участником")
    })
    public ResponseEntity<List<ItemDetailsResponse>> itemsOfMember(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if(!memberService.memberExists(id)) {
            return ResponseEntity.notFound().build();
        }

        var member = memberService.getMember(id);
        var user = userService.getUser(principal);
        if(memberService.userInClan(user, member.getClan())) {
            var items = member.getInventory();
            var response = itemFacade.itemDetailsToResponse(items);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(403).build();
        }
    }



    @PostMapping("/{item_id}/share/member/{member_id}")
    @Operation(description = "Подарить предмет соклановцу")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Неверно указано число предметов (<= 0)"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может подарить данный предмет данному участнику"),
            @ApiResponse(responseCode = "404", description = "Не найден предмет или участник"),
            @ApiResponse(responseCode = "406", description = "Не хватает предметов для передачи")
    })
    public ResponseEntity<ItemTransferResponse> shareItem(
            @PathVariable("item_id") Long itemId,
            @PathVariable("member_id") Long memberId,
            @Param("amount") int amount,
            Principal principal
    ) {
        if(!(itemService.itemExist(itemId) && memberService.memberExists(memberId))) {
            return ResponseEntity.notFound().build();

        } else if(amount <= 0) {
            return ResponseEntity.badRequest().build();

        }

        var item = itemService.getItem(itemId);
        var to = memberService.getMember(memberId);
        var user = userService.getUser(principal);
        if(!item.getClan().equals(to.getClan())
                || !memberService.userInClan(user, to.getClan())) {
            return ResponseEntity.status(403).build();
        }

        var from = memberService.getMember(principal, to.getClan());
        if(!from.equals(to) && from.memberHasEnoughItems(item, amount)) {
            var itemTransfer = iItemTransferService.transferItem(from, to, item, amount);
            var response = itemTransferFacade.itemTransferToResponse(itemTransfer);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(406).build();
    }


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, value = "/{id}/image")
    @Operation(description = "Загрузить фото для предмета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Неверный формат файла"),
            @ApiResponse(responseCode = "404", description = "Предмет не найден"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может изменить предмет")
    })
    public ResponseEntity<ItemResponse> uploadImage(
            @PathVariable("id") Long id,
            @RequestParam("file") MultipartFile file,
            Principal principal

    ) {
        if(!itemService.itemExist(id)) {
            return ResponseEntity.notFound().build();

        } else if(!Objects.requireNonNull(file.getContentType()).startsWith("image")) {
            return ResponseEntity.badRequest().build();

        }

        var user = userService.getUser(principal);
        var item = itemService.getItem(id);

        if(memberService.userHaveStatusInClan(user, item.getClan(), Member.MemberStatus.CAN_MANAGE_ITEMS)) {
            item = itemService.uploadImg(item, file);
            var response = itemFacade.itemToItemResponse(item);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(403).build();
    }

    @DeleteMapping("/{id}/image")
    @Operation(description = "Удалить фото предмета")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Предмет не найден"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может изменить предмет")
    })
    public ResponseEntity<ItemResponse> deleteImage(
            @PathVariable("id") Long id,
            Principal principal

    ) {
        if(!itemService.itemExist(id)) {
            return ResponseEntity.notFound().build();
        }

        var user = userService.getUser(principal);
        var item = itemService.getItem(id);

        if(memberService.userHaveStatusInClan(user, item.getClan(), Member.MemberStatus.CAN_MANAGE_ITEMS)) {
            item = itemService.deleteImg(item);
            var response = itemFacade.itemToItemResponse(item);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(403).build();
    }


}
