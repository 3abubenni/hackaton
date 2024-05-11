package com.hackaton.hackatonv100.facade;

import com.hackaton.hackatonv100.model.Item;
import com.hackaton.hackatonv100.model.ItemDetails;
import com.hackaton.hackatonv100.model.response.ItemDetailsResponse;
import com.hackaton.hackatonv100.model.response.ItemResponse;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ItemFacade {

    public ItemResponse itemToItemResponse(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .cost(item.getCost())
                .name(item.getName())
                .img(item.getImg())
                .description(item.getDescription())
                .build();
    }

    public List<ItemResponse> itemsToItemsResponse(Collection<Item> items) {
        return items.stream()
                .map(this::itemToItemResponse)
                .collect(Collectors.toList());
    }

    public ItemDetailsResponse itemDetailsToResponse(ItemDetails itemDetails) {
        return ItemDetailsResponse.builder()
                .count(itemDetails.getAmount())
                .description(itemDetails.getItem().getDescription())
                .name(itemDetails.getItem().getName())
                .cost(itemDetails.getItem().getCost())
                .img(itemDetails.getItem().getImg())
                .id(itemDetails.getItem().getId())
                .build();
    }

    public List<ItemDetailsResponse> itemDetailsToResponse(Collection<ItemDetails> itemDetails) {
        return itemDetails.stream()
                .map(this::itemDetailsToResponse)
                .collect(Collectors.toList());
    }

}
