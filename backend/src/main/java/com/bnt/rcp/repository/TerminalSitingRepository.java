package com.bnt.rcp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bnt.rcp.entity.TerminalSiting;

@Repository
public interface TerminalSitingRepository extends JpaRepository<TerminalSiting, Integer> {
    Optional<TerminalSiting> findByName(String name);
    Optional<TerminalSiting> findByNameAndDeletedNot(String name, Integer deleted);
}