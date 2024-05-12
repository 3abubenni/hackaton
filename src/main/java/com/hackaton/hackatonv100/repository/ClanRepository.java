package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.Clan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClanRepository extends JpaRepository<Clan, Long> {

    @Query("SELECT c FROM Clan c WHERE lower(c.name) LIKE ?1 " +
            "ORDER BY c.name LIMIT ?2")
    List<Clan> searchByName(String name, Long limit);

    @Query("SELECT COUNT(c) > 0 FROM Clan c WHERE c.name=?1")
    boolean existsByName(String name);

}
