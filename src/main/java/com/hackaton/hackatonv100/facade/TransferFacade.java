package com.hackaton.hackatonv100.facade;

import com.hackaton.hackatonv100.model.Transfer;
import com.hackaton.hackatonv100.model.response.TransferResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TransferFacade {

    public TransferResponse transferToTransferResponse(Transfer transfer) {
        return TransferResponse.builder()
                .date(transfer.getDate())
                .money(transfer.getMoney())
                .memberFrom(transfer.getFrom().getMember().getId())
                .memberTo(transfer.getTo().getMember().getId())
                .id(transfer.getId())
                .build();
    }

    public List<TransferResponse> transfersToTransfersResponse(Collection<Transfer> transfers) {
        return transfers.stream().map(this::transferToTransferResponse).collect(Collectors.toList());
    }

}
