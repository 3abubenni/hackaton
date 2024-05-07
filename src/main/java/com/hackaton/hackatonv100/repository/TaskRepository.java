package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.Task;
import com.hackaton.hackatonv100.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    List<Task> findAllBySolver(Member member);

    @Transactional
    @Query("SELECT t FROM Task t WHERE t.solver.user=?1")
    List<Task> findAllByUser(User user);

    @Transactional
    @Modifying
    @Query("SELECT t FROM Task t WHERE t.clan=?1")
    List<Task> findAllByClan(Clan clan);

}
