package com.hackaton.hackatonv100.service;

import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Transfer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ITransferService {

    Transfer makeTransfer(Member from, Member to, int money);
    List<Transfer> getAllTransfersOfMember(Member member);
    List<Transfer> getTransfersFromMember(Member member);
    List<Transfer> getTransfersToMember(Member member);



}
