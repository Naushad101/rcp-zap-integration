package com.bnt.rcp.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bnt.rcp.entity.TerminalModel;

@Repository
public interface TerminalModelRepository extends JpaRepository<TerminalModel, Integer> {
}
