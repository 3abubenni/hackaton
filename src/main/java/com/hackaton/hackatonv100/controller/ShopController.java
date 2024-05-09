package com.hackaton.hackatonv100.controller;

import com.hackaton.hackatonv100.facade.ItemFacade;
import com.hackaton.hackatonv100.facade.PurchaseFacade;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.response.ItemDetailsResponse;
import com.hackaton.hackatonv100.model.response.PurchaseResponse;
import com.hackaton.hackatonv100.service.clan.IClanService;
import com.hackaton.hackatonv100.service.clan.IItemService;
import com.hackaton.hackatonv100.service.clan.IMemberService;
import com.hackaton.hackatonv100.service.operation.IPurchaseService;
import com.hackaton.hackatonv100.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Tag(name = "Shop controller", description = "Контроллер для магазина клана")
@RequestMapping("/api/shop")
@AllArgsConstructor
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
public class ShopController {


    private IItemService itemService;
    private IPurchaseService purchaseService;
    private IUserService userService;
    private IClanService clanService;
    private ItemFacade itemFacade;
    private PurchaseFacade purchaseFacade;
    private IMemberService memberService;

    @GetMapping("/clan/item/{id}")
    @Operation(description = "Получить доступные к покупке предметы в магазине клана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Клан не найден"),
            @ApiResponse(responseCode = "403", description = "Пользователь не в клане")
    })
    public ResponseEntity<List<ItemDetailsResponse>> shopOfClan(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if (!clanService.clanExists(id)) {
            return ResponseEntity.notFound().build();

        } else if (!memberService.userInClan(principal, id)) {
            return ResponseEntity.status(403).build();

        }

        var clan = clanService.getClan(id);
        var response = itemFacade.itemDetailsToResponse(clan.getShop());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/buy/item/{id}")
    @Operation(description = "Купить предмет")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Указано неверное количество предметов (<=0 или больше имеющегося количества"),
            @ApiResponse(responseCode = "403", description = "Пользователь не состоит в клане"),
            @ApiResponse(responseCode = "404", description = "Предмет не найден"),
            @ApiResponse(responseCode = "406", description = "У участника клана не хватает средств")
    })
    public ResponseEntity<PurchaseResponse> buyItem(
            @PathVariable("id") Long id,
            @Param("amount") int amount,
            Principal principal
    ) {
        if (!itemService.itemExist(id)) {
            return ResponseEntity.notFound().build();
        } else if (amount <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var item = itemService.getItem(id);
        var user = userService.getUser(principal);
        if (!memberService.userInClan(user, item.getClan())) {
            return ResponseEntity.status(403).build();
        }
        var member = memberService.getMember(user, item.getClan());
        var itemDetails = item.getClan().getItemDetailsFromShop(item);
        if(itemDetails.getAmount() < amount) {
            return ResponseEntity.badRequest().build();

        } else if(item.getCost() * amount > member.getMoney()) {
            return ResponseEntity.status(406).build();

        } else {
            var purchase = purchaseService.makePurchase(item, member, amount);
            var response = purchaseFacade.purchaseToResponse(purchase);
            return ResponseEntity.ok(response);
        }
    }



    @PostMapping("/add/item/{id}")
    @Operation(description = "Добавить предметы данного типа в магазин клана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Количество предметов <= 0"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может добавлять предметы"),
            @ApiResponse(responseCode = "404", description = "Предмет не найден")
    })
    public ResponseEntity<ItemDetailsResponse> addItem(
            @PathVariable("id") Long id,
            @Param("amount") int amount,
            Principal principal
    ) {
        if(!itemService.itemExist(id)) {
            return ResponseEntity.notFound().build();

        } else if(amount <= 0) {
            return ResponseEntity.badRequest().build();

        }

        var item = itemService.getItem(id);

        if(memberService.userHaveStatusInClan(principal, item.getClan(), Member.MemberStatus.CAN_MANAGE_ITEMS)) {
            var details = itemService.addItems(item, amount);
            var response = itemFacade.itemDetailsToResponse(details);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(403).build();
        }
    }

}
