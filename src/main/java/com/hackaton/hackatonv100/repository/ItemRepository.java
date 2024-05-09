package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Item;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Transactional
    @Query("SELECT count(i) > 0 FROM Item i WHERE i.name=?1 AND i.clan.id=?2")
    boolean itemExistByClanIdAndName(String name, Long clanId);

    @Transactional
    @Query("SELECT count(i) > 0 FROM Item i WHERE i.id=?2 AND i.clan.id=?1")
    boolean itemExistByClanIdAndId(Long itemId, Long clanId);

}
