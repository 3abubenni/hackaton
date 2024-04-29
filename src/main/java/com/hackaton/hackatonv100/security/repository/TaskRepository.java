package com.hackaton.hackatonv100.security.repository;

import com.hackaton.hackatonv100.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
