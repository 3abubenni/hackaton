package com.hackaton.hackatonv100.service;

import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Operation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOperationService {

    Operation addMoney(Member member, int money, Operation.OperationType type);
    Operation withdrawMoney(Member member, int money, Operation.OperationType type);
    List<Operation> getOperationsOfMember(Member member);
    Operation getOperation(Long idOperation);
    boolean operationExists(Long idOperation);

}