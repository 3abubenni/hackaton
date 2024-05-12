package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.Clan;
import com.hackaton.hackatonv100.model.Member;
import com.hackaton.hackatonv100.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    List<Member> findAllByUser(User user);
    boolean existsByUserAndClan(User user, Clan clan);
    Optional<Member> findByUserAndClan(User user, Clan clan);
    Optional<Member> findByStatusAndClan(int status, Clan clan);

    @Modifying
    @Query(value = "SELECT * FROM member WHERE clan_id=?1", nativeQuery = true)
    List<Member> findAllByIdClan(Long clanId);

    @Modifying
    @Query(value = "SELECT m FROM Member m WHERE m.clan=?1")
    List<Member> findAllByClan(Clan clan);

    //Check that user has clan with same status
    @Query("SELECT COUNT(m) > 0 FROM Member m WHERE m.user=?1 " +
            "AND status=?2")
    boolean userHasClanWithStatus(User user, int status);

}
