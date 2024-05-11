package com.hackaton.hackatonv100.service.operation.impl;

import com.hackaton.hackatonv100.model.Item;
import com.hackaton.hackatonv100.model.ItemTransfer;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.repository.ItemTransferRepository;
import com.hackaton.hackatonv100.service.operation.IItemTransferService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class ItemTransferService implements IItemTransferService {

    private ItemTransferRepository transferRepository;

    @Override
    public ItemTransfer transferItem(Member from, Member to, Item item, int amount) {
        from.dropItem(item, amount);
        var details = to.addItem(item, amount);

        var transfer = ItemTransfer.builder()
                .date(new Date(System.currentTimeMillis()))
                .itemDetails(details)
                .from(from)
                .to(to)
                .build();

        return transferRepository.save(transfer);
    }

    @Override
    public List<ItemTransfer> transferOfMember(Member member) {
        return transferRepository.findAllByMember(member);
    }

    @Override
    public ItemTransfer getTransfer(Long id) {
        return transferRepository.findById(id).orElseThrow();
    }

    @Override
    public boolean transferExists(Long id) {
        return transferRepository.existsById(id);
    }
}
