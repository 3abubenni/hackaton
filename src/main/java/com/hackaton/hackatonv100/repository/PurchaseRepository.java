package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.Item;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("SELECT p FROM Purchase p WHERE p.operation.member=?1")
    List<Purchase> findByMember(Member member);
    @Query("SELECT p FROM Purchase p WHERE p.itemDetails.item=?1")
    List<Purchase> findPurchaseByItem(Item item);

}
