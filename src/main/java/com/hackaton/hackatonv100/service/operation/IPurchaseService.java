package com.hackaton.hackatonv100.service.operation;

import com.hackaton.hackatonv100.model.Item;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Purchase;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IPurchaseService {

    Purchase makePurchase(Item item, Member member, int amount);
    List<Purchase> purchasesOfMember(Member member);
    List<Purchase> purchasesOfItem(Item item);

}
