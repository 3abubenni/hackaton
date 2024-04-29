package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Invite;
import com.hackaton.hackatonv100.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

    List<Invite> findByUser(User user);
    List<Invite> findByClan(Clan clan);

    @Modifying
    @Query("DELETE Invite i WHERE i.user=?1")
    @Transactional
    void deleteAllByUser(User user);
    @Modifying
    @Query("DELETE Invite i WHERE i.clan=?1")
    @Transactional
    void deleteAllByClan(Clan clan);

}
