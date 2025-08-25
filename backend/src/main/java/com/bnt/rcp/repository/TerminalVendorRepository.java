package com.bnt.rcp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bnt.rcp.entity.TerminalVendor;

@Repository
public interface TerminalVendorRepository extends JpaRepository<TerminalVendor, Integer> {
   Optional<TerminalVendor> findByName(String name);
    Optional<TerminalVendor> findByNameAndDeletedNot(String name, Integer deleted);
}
