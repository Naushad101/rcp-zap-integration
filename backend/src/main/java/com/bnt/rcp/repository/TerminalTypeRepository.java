package com.bnt.rcp.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bnt.rcp.entity.TerminalTypeEntity;

@Repository
public interface TerminalTypeRepository extends JpaRepository<TerminalTypeEntity, Integer> {
    Optional<TerminalTypeEntity> findByType(String type);
    Optional<TerminalTypeEntity> findByTypeAndDeletedNot(String type, Integer deleted);
}