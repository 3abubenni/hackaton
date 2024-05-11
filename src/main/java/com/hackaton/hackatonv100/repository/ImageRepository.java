package com.hackaton.hackatonv100.repository;

import com.hackaton.hackatonv100.model.Image;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT i FROM Image i WHERE i.name=?1")
    Optional<Image> findByName(String name);

    @Query("SELECT COUNT(i) > 0 FROM Image i WHERE i.name=?1")
    boolean existsByName(String name);

    @Query("DELETE FROM Image i WHERE i.name=?1")
    @Transactional
    @Modifying
    void deleteByName(String name);

    @Query("DELETE FROM Image i WHERE i.name IN ?1")
    @Transactional
    @Modifying
    void deleteByNames(List<String> names);

}
