package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE " +
            "u.fname LIKE ?1 " +
            "OR lower(u.lname) LIKE ?1 " +
            "OR lower(u.email) LIKE ?1 " +
            "OR CONCAT(lower(u.fname), ' ', lower(u.lname)) LIKE ?1 " +
            "ORDER BY u.fname LIMIT ?2")
    List<User> searchUser(String query, Long limit);

}
