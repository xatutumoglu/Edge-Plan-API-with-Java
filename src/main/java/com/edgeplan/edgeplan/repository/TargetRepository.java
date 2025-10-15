package com.edgeplan.edgeplan.repository;

import com.edgeplan.edgeplan.model.Target;
import com.edgeplan.edgeplan.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TargetRepository extends JpaRepository<Target, Long> {
    List<Target> findByUser(User user);
}