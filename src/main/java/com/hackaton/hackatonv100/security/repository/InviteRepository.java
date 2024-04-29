package com.hackaton.hackatonv100.security.repository;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Invite;
import com.hackaton.hackatonv100.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InviteRepository extends JpaRepository<Invite, Long> {

    List<Invite> findByUser(User user);
    List<Invite> findByClan(Clan clan);

}
