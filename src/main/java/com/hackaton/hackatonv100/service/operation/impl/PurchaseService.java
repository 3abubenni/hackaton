package com.hackaton.hackatonv100.service.operation.impl;

import com.hackaton.hackatonv100.model.Item;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Operation;
import com.hackaton.hackatonv100.model.Purchase;
import com.hackaton.hackatonv100.repository.PurchaseRepository;
import com.hackaton.hackatonv100.service.operation.IPurchaseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class PurchaseService implements IPurchaseService {

    private PurchaseRepository purchaseRepository;

    @Override
    public Purchase makePurchase(Item item, Member member, int amount) {
        if(member.getMoney() - item.getCost() < 0) {
            throw new RuntimeException("Member don't have enough money");
        } else if(amount <= 0) {
            throw new RuntimeException("Amount of items can't be less 0");
        }

        var details = member.buyItem(item, amount);
        item.getClan().sellItem(item, amount);
        var operation = Operation.builder()
                .date(new Date(System.currentTimeMillis()))
                .member(member)
                .type(Operation.OperationType.BUYING_ITEM.code)
                .money(item.getCost())
                .build();

        var purchase = Purchase.builder()
                .amount(amount)
                .itemDetails(details)
                .operation(operation)
                .build();

        return purchaseRepository.save(purchase);
    }

    @Override
    public List<Purchase> purchasesOfMember(Member member) {
        return purchaseRepository.findByMember(member);
    }

    @Override
    public List<Purchase> purchasesOfItem(Item item) {
        return purchaseRepository.findPurchaseByItem(item);
    }
}
