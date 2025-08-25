package com.bnt.rcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bnt.rcp.entity.TerminalAddressDetail;

@Repository
public interface TerminalAddressDetailRepository extends JpaRepository<TerminalAddressDetail, Long> {
    // Additional custom queries can be added here if needed
}
