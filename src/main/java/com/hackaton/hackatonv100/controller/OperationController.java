package com.hackaton.hackatonv100.controller;

import com.hackaton.hackatonv100.facade.OperationFacade;
import com.hackaton.hackatonv100.facade.PurchaseFacade;
import com.hackaton.hackatonv100.facade.TransferFacade;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.response.OperationResponse;
import com.hackaton.hackatonv100.model.response.PurchaseResponse;
import com.hackaton.hackatonv100.model.response.TransferResponse;
import com.hackaton.hackatonv100.service.clan.IMemberService;
import com.hackaton.hackatonv100.service.operation.IOperationService;
import com.hackaton.hackatonv100.service.operation.IPurchaseService;
import com.hackaton.hackatonv100.service.operation.ITransferService;
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

@Controller
@AllArgsConstructor
@Tag(description = "Контроллер для проведения операций над записью участника в клане", name = "Operation Controller")
@RequestMapping("/api/operation")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OperationController {

    private IOperationService operationService;
    private ITransferService transferService;
    private IMemberService memberService;
    private IUserService userService;
    private IPurchaseService purchaseService;
    private PurchaseFacade purchaseFacade;
    private TransferFacade transferFacade;
    private OperationFacade operationFacade;


    @PostMapping("/send_money/member/{id}")
    @Operation(description = "Отправить деньги на счёт другому участнику клана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "Количество денег <= 0"),
            @ApiResponse(responseCode = "404", description = "Участник клана не найден"),
            @ApiResponse(responseCode = "406", description = "Пользователь не состоит в клане или пытается перевести сам себе"),
            @ApiResponse(responseCode = "420", description = "Недостаточно средств на счету участника клана")
    })
    public ResponseEntity<TransferResponse> sendMoney(
            @PathVariable("id") Long id,
            @Param("money") int money,
            Principal principal
    ) {
        if (money <= 0) {
            return ResponseEntity.badRequest().build();

        } else if (!memberService.memberExists(id)) {
            return ResponseEntity.notFound().build();

        }

        var to = memberService.getMember(id);
        var user = userService.getUser(principal);
        if (!memberService.userInClan(user, to.getClan()) || to.getUser().equals(user)) {
            return ResponseEntity.status(406).build();

        } else {
            var from = memberService.getMember(user, to.getClan());
            if (from.getMoney() - money < 0) {
                return ResponseEntity.status(420).build();
            }
            var transfer = transferService.makeTransfer(from, to, money);
            var response = transferFacade.transferToTransferResponse(transfer);
            return ResponseEntity.ok(response);
        }
    }


    @GetMapping("/member/{id}")
    @Operation(description = "Получить всю информацию об операциях участника клана. " +
            "Данную информацию может получить только сам участник или админ клана")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Не найден"),
            @ApiResponse(responseCode = "406", description = "Пользователь не состоит в клане или не может посмотреть операции")
    })
    public ResponseEntity<List<OperationResponse>> operationsOfMember(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if (!memberService.memberExists(id)) {
            return ResponseEntity.notFound().build();
        }

        var member = memberService.getMember(id);
        var user = userService.getUser(principal);
        if (member.getUser().equals(user)
                || memberService.userHaveStatusInClan(user, member.getClan(), Member.MemberStatus.ADMIN)) {
            var response = getOperationsResponseByMember(member);
            return ResponseEntity.ok(response);

        }
        return ResponseEntity.status(406).build();
    }


    @GetMapping("/transfer/member/{id}")
    @Operation(description = "Получить все переводы участника (входящие и исходящии). " +
            "Переводы может получить только сам участник, которому он принадлежит и админ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Участник не найден"),
            @ApiResponse(responseCode = "406", description = "Пользователь не состоит в клане или не может посмотреть переводы")
    })
    public ResponseEntity<List<TransferResponse>> getTransfersOfMember(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if (!memberService.memberExists(id)) {
            return ResponseEntity.notFound().build();
        }

        var member = memberService.getMember(id);
        var user = userService.getUser(principal);
        if (member.getUser().equals(user)
                || memberService.userHaveStatusInClan(user, member.getClan(), Member.MemberStatus.ADMIN)) {
            var response = getTransfersResponseByMember(member);
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.status(406).build();
    }


    @GetMapping("/purchase/member/{id}")
    @Operation(description = "Получить все покупки участника")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Участник не найден"),
            @ApiResponse(responseCode = "403", description = "Пользователь не может получить данные")
    })
    public ResponseEntity<List<PurchaseResponse>> purchasesOfMember(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if (!memberService.memberExists(id)) {
            return ResponseEntity.notFound().build();
        }

        var member = memberService.getMember(id);
        var user = userService.getUser(principal);
        if (member.getUser().equals(user)
                || memberService.userHaveStatusInClan(user, member.getClan(), Member.MemberStatus.ADMIN)) {
            var purchases = purchaseService.purchasesOfMember(member);
            var response = purchaseFacade.purchasesToResponse(purchases);
            return ResponseEntity.ok(response);

        } else {
            return ResponseEntity.status(403).build();
        }
    }


    @GetMapping("/{id}")
    @Operation(description = "Получить операцию по id. Данное действие может произвести только сам участник, " +
            "которому принадлежит операция или админ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Операция не найдена"),
            @ApiResponse(responseCode = "406", description = "Пользователь не может получить информацию о операции")
    })
    public ResponseEntity<OperationResponse> getOperation(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if (!operationService.operationExists(id)) {
            return ResponseEntity.notFound().build();

        }

        var operation = operationService.getOperation(id);
        var user = userService.getUser(principal);
        if (operation.getMember().getUser().equals(user)
                || memberService.userHaveStatusInClan(user, operation.getMember().getClan(), Member.MemberStatus.ADMIN)) {
            return ResponseEntity.ok(operationFacade.operationToOperationResponse(operation));
        }
        return ResponseEntity.status(406).build();
    }


    @GetMapping("/transfer/{id}")
    @Operation(description = "Получить перевод по id. Данное действие может произвести только сам участник, " +
            "которому принадлежит перевод или админ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Операция не найдена"),
            @ApiResponse(responseCode = "406", description = "Пользователь не может получить информацию о операции")
    })
    public ResponseEntity<TransferResponse> getTransfer(
            @PathVariable("id") Long id,
            Principal principal
    ) {
        if (!transferService.transferExists(id)) {
            return ResponseEntity.notFound().build();
        }

        var transfer = transferService.getTransfer(id);
        var user = userService.getUser(principal);
        if (transfer.getTo().getMember().getUser().equals(user)
                || transfer.getFrom().getMember().getUser().equals(user)
                || memberService.userHaveStatusInClan(user, transfer.getTo().getMember().getClan(), Member.MemberStatus.ADMIN)
        ) {
            return ResponseEntity.ok(transferFacade.transferToTransferResponse(transfer));
        }

        var checker = memberService.getMember(user, transfer.getTo().getMember().getClan());
        if (checker.checkStatus(Member.MemberStatus.ADMIN)) {
            return ResponseEntity.ok(transferFacade.transferToTransferResponse(transfer));
        }
        return ResponseEntity.status(406).build();
    }


    /*
     *
     *
     * Just get list of operations user and cast it to response by facade (or transfer)
     *
     */
    private List<OperationResponse> getOperationsResponseByMember(Member member) {
        var operations = operationService.getOperationsOfMember(member);
        return operationFacade.operationsToOperationsResponse(operations);
    }

    private List<TransferResponse> getTransfersResponseByMember(Member member) {
        var transfers = transferService.getAllTransfersOfMember(member);
        return transferFacade.transfersToTransfersResponse(transfers);
    }
}
