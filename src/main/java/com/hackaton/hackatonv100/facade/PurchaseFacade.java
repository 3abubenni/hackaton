package com.hackaton.hackatonv100.facade;

import com.hackaton.hackatonv100.model.Purchase;
import com.hackaton.hackatonv100.model.response.PurchaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class PurchaseFacade {

    private ItemFacade itemFacade;

    public PurchaseResponse purchaseToResponse(Purchase purchase) {
        var itemDetailsResponse = itemFacade.itemDetailsToResponse(purchase.getItemDetails());
        return PurchaseResponse.builder()
                .id(purchase.getId())
                .member(purchase.getOperation().getMember().getId())
                .money(purchase.getOperation().getMoney())
                .itemResponse(itemDetailsResponse)
                .date(purchase.getOperation().getDate())
                .build();
    }

    public List<PurchaseResponse> purchasesToResponse(Collection<Purchase> purchases) {
        return purchases.stream()
                .map(this::purchaseToResponse)
                .collect(Collectors.toList());
    }

}
