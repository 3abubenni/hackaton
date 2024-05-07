package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> { }
