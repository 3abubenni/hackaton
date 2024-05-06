package com.hackaton.hackatonv100.service.impl;

import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Operation;
import com.hackaton.hackatonv100.repository.OperationRepository;
import com.hackaton.hackatonv100.service.IOperationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class OperationService implements IOperationService {

    private OperationRepository operationRepository;

    @Override
    public Operation addMoney(Member member, int money) {
        if(money <= 0) {
            throw new RuntimeException("Value of money is less or equals 0");
        }
        var operation = addOperation(member, money);
        member.setMoney(member.getMoney() + money);
        return operationRepository.save(operation);
    }

    @Override
    public Operation withdrawMoney(Member member, int money) {
        if(money <= 0) {
            throw new RuntimeException("Value of money is less or equals 0");
        }

        if(member.getMoney() - money < 0) {
            throw new RuntimeException("Member don't have enough money");
        }

        var operation = addOperation(member, money);
        member.setMoney(member.getMoney() - money);
        return operationRepository.save(operation);
    }

    @Override
    public List<Operation> getOperationOfMember(Long idMember) {
        return operationRepository.findAllByIdMember(idMember);
    }

    @Override
    public Operation getOperation(Long id) {
        return operationRepository.findById(id).orElseThrow();
    }

    @Override
    public boolean operationExists(Long id) {
        return operationRepository.existsById(id);
    }

    private Operation addOperation(Member member, int money) {
        var operation = Operation.builder()
                .date(new Date(System.currentTimeMillis()))
                .member(member)
                .money(money)
                .build();
        member.getOperations().add(operation);
        return operation;
    }

}
