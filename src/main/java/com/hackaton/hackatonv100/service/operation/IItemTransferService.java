package com.hackaton.hackatonv100.service.operation;

import com.hackaton.hackatonv100.model.Item;
import com.hackaton.hackatonv100.model.ItemTransfer;
import com.hackaton.hackatonv100.model.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IItemTransferService {

    ItemTransfer transferItem(Member from, Member to, Item item, int count);
    List<ItemTransfer> transferOfMember(Member member);
    ItemTransfer getTransfer(Long id);
    boolean transferExists(Long id);

}
