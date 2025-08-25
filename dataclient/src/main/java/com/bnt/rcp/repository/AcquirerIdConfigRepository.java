package com.bnt.rcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bnt.rcp.entity.AcquirerIdConfig;

@Repository
public interface AcquirerIdConfigRepository extends JpaRepository<AcquirerIdConfig, Integer> {

}
