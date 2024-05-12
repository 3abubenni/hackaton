package com.hackaton.hackatonv100.service.clan;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Item;
import com.hackaton.hackatonv100.model.ItemDetails;
import com.hackaton.hackatonv100.model.requests.ItemRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface IItemService {

    Item createItem(ItemRequest request, Clan clan);
    Item updateItem(ItemRequest request, Long id);
    boolean itemExist(Long id);
    Item getItem(Long id);
    void deleteItem(Long id);
    boolean itemWithThisNameExistsInClan(String name, Long clanId);
    boolean itemExistsInClan(Long clanId, Long itemId);
    ItemDetails addItems(Item item, int amount);
    Item uploadImg(Item item, MultipartFile file);
    Item deleteImg(Item item);


}
