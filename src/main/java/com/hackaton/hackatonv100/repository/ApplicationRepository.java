package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.ApplicationModel;
import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationModel, Long> {

    List<ApplicationModel> findAllByUser(User user);
    List<ApplicationModel> findAllByClan(Clan clan);

    @Query("DELETE ApplicationModel a WHERE a.clan.id=?1")
    @Modifying
    @Transactional
    void deleteAllByIdClan(long clan);
    @Query("DELETE ApplicationModel a WHERE a.user=?1")
    @Modifying
    @Transactional
    void deleteAllByUser(User user);

}
