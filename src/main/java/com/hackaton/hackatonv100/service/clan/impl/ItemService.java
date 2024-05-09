package com.hackaton.hackatonv100.service.clan.impl;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Item;
import com.hackaton.hackatonv100.model.ItemDetails;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.requests.ItemRequest;
import com.hackaton.hackatonv100.repository.ItemRepository;
import com.hackaton.hackatonv100.service.clan.IItemService;
import com.hackaton.hackatonv100.service.operation.IPurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ItemService implements IItemService {

    private ItemRepository itemRepository;

    @Override
    public Item createItem(ItemRequest request, Clan clan) {
        var item = Item.builder()
                .cost(request.getCost())
                .description(request.getDescription())
                .name(request.getName())
                .clan(clan)
                .build();

        var itemDetails = ItemDetails.builder()
                .item(item)
                .amount(request.getAmount())
                .build();

        clan.getShop().add(itemDetails);
        return itemRepository.save(item);
    }

    @Override
    public Item updateItem(ItemRequest request, Long id) {
        var item = itemRepository.findById(id).orElseThrow();
        item.setDescription(request.getDescription());
        item.setName(request.getDescription());
        item.setCost(request.getCost());
        var itemDetails = item.getClan().getItemDetailsFromShop(item);
        itemDetails.setAmount(request.getAmount());
        return itemRepository.save(item);
    }

    @Override
    public boolean itemExist(Long id) {
        return itemRepository.existsById(id);
    }

    @Override
    public Item getItem(Long id) {
        return itemRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    @Override
    public boolean itemWithThisNameExistsInClan(String name, Long clanId) {
        return itemRepository.itemExistByClanIdAndName(name, clanId);
    }

    @Override
    public boolean itemExistsInClan(Long clanId, Long itemId) {
        return itemRepository.itemExistByClanIdAndId(itemId, clanId);
    }

    @Override
    public ItemDetails addItems(Item item, int amount) {
        var details = item.getClan().getItemDetailsFromShop(item);
        details.setAmount(details.getAmount() + amount);
        itemRepository.save(item);
        return details;
    }


}
