package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Modifying
    @Query("SELECT o FROM Operation o WHERE o.member=?1")
    List<Operation> findAllByMember(Member member);

}
