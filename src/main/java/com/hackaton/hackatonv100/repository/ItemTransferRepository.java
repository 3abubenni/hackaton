package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.ItemTransfer;
import com.hackaton.hackatonv100.model.Member;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemTransferRepository extends JpaRepository<ItemTransfer, Long> {

    @Query("SELECT i FROM ItemTransfer i WHERE i.from=?1 OR i.to=?1")
    @Transactional
    List<ItemTransfer> findAllByMember(Member member);


}
