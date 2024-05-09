package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Transfer;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Modifying
    @Transactional
    @Query("SELECT t FROM Transfer t WHERE t.from.member=?1 OR t.to.member=?1")
    List<Transfer> findAllByMember(Member member);

    @Modifying
    @Transactional
    @Query("SELECT t FROM Transfer t WHERE t.from.member=?1")
    List<Transfer> findAllByMemberFrom(Member member);

    @Modifying
    @Transactional
    @Query("SELECT t FROM Transfer t WHERE t.to.member=?1")
    List<Transfer> findAllByMemberTo(Member member);

}
