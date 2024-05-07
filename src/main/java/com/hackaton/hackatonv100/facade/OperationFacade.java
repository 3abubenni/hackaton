package com.hackaton.hackatonv100.facade;

import com.hackaton.hackatonv100.model.Operation;
import com.hackaton.hackatonv100.model.response.OperationResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OperationFacade {

    public OperationResponse operationToOperationResponse(Operation operation) {
        return OperationResponse.builder()
                .id(operation.getId())
                .type(operation.getType())
                .money(operation.getMoney())
                .date(operation.getDate())
                .member(operation.getMember().getId())
                .build();
    }

    public List<OperationResponse> operationsToOperationsResponse(Collection<Operation> operations) {
        return operations.stream()
                .map(this::operationToOperationResponse)
                .collect(Collectors.toList());
    }
}
