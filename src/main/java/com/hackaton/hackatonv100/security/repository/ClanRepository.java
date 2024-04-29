package com.hackaton.hackatonv100.security.repository;

import com.hackaton.hackatonv100.model.Clan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClanRepository extends JpaRepository<Clan, Long> {



}
