package com.hackaton.hackatonv100.service.operation.impl;

import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Operation;
import com.hackaton.hackatonv100.model.Transfer;
import com.hackaton.hackatonv100.repository.TransferRepository;
import com.hackaton.hackatonv100.service.operation.ITransferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class TransferService implements ITransferService {

    private TransferRepository transferRepository;

    @Override
    public Transfer makeTransfer(Member from, Member to, int money) {
        if(money <= 0) {
            throw new RuntimeException("Money can't be less 0");
        } else if(from.getMoney() - money < 0) {
            throw new RuntimeException("Member don't have enough money");
        }

        from.withdrawMoney(money);
        to.addMoney(money);

        var now = new Date(System.currentTimeMillis());
        var operationFrom = buildOperation(from, money, Operation.OperationType.TRANSFER_FROM, now);
        var operationTo = buildOperation(to, money, Operation.OperationType.TRANSFER_TO, now);
        var transfer = Transfer.builder()
                .date(now)
                .money(money)
                .from(operationFrom)
                .to(operationTo)
                .build();

        return transferRepository.save(transfer);
    }

    @Override
    public List<Transfer> getAllTransfersOfMember(Member member) {
        return transferRepository.findAllByMember(member);
    }

    @Override
    public List<Transfer> getTransfersFromMember(Member member) {
        return transferRepository.findAllByMemberFrom(member);
    }

    @Override
    public List<Transfer> getTransfersToMember(Member member) {
        return transferRepository.findAllByMemberTo(member);
    }

    @Override
    public Transfer getTransfer(Long id) {
        return transferRepository.findById(id).orElseThrow();
    }

    @Override
    public boolean transferExists(Long id) {
        return transferRepository.existsById(id);
    }

    private Operation buildOperation(Member member, int money, Operation.OperationType type, Date date) {
        return Operation.builder()
                .money(money)
                .type(type.code)
                .member(member)
                .date(date)
                .build();
    }

}
