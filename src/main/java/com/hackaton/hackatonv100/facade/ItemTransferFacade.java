package com.hackaton.hackatonv100.facade;

import com.hackaton.hackatonv100.model.ItemTransfer;
import com.hackaton.hackatonv100.model.response.ItemTransferResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ItemTransferFacade {

    private ItemFacade itemFacade;


    public ItemTransferResponse itemTransferToResponse(ItemTransfer transfer) {
        var item = itemFacade.itemDetailsToResponse(transfer.getItemDetails());
        return ItemTransferResponse.builder()
                .id(transfer.getId())
                .date(transfer.getDate())
                .memberFrom(transfer.getFrom().getId())
                .memberTo(transfer.getTo().getId())
                .item(item)
                .build();
    }

    public List<ItemTransferResponse> itemTransfersToResponse(Collection<ItemTransfer> transfers) {
        return transfers.stream()
                .map(this::itemTransferToResponse)
                .collect(Collectors.toList());
    }

}
