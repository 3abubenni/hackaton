package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.ApplicationModel;
import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationModel, Long> {

    List<ApplicationModel> findAllByUser(User user);
    List<ApplicationModel> findAllByClan(Clan clan);
    void deleteAllByClan(Clan clan);
    void deleteAllByUser(User user);

}
