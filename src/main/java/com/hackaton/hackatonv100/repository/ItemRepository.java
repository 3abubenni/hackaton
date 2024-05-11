package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("SELECT count(i) > 0 FROM Item i WHERE i.name=?1 AND i.clan.id=?2")
    boolean itemExistByClanIdAndName(String name, Long clanId);

    @Query("SELECT count(i) > 0 FROM Item i WHERE i.id=?2 AND i.clan.id=?1")
    boolean itemExistByClanIdAndId(Long itemId, Long clanId);

    @Query(value = "DELETE FROM clan_shop WHERE shop_id IN " +
            "(SELECT id FROM item_details WHERE item_id=?1)", nativeQuery = true)
    void deleteFromShopById(Long id);

    @Query(value = "DELETE FROM member_inventory WHERE inventory_id IN " +
            "(SELECT id FROM item_details WHERE item_id=?1)", nativeQuery = true)
    void deleteFromInventoryById(Long id);

}
