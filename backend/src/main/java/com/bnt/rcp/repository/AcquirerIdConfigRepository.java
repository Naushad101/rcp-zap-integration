package com.bnt.rcp.repository;

import com.bnt.rcp.entity.AcquirerIdConfig;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AcquirerIdConfigRepository extends JpaRepository<AcquirerIdConfig, Integer> {

    boolean existsByName(String name);

    boolean existsByCode(String code);

    @Query("SELECT a FROM AcquirerIdConfig a WHERE a.active = '1' AND a.deleted = '0'")
    List<AcquirerIdConfig> findAllAcquirerIdConfigsIdAndName();

    @Transactional
    @Modifying
    @Query("UPDATE AcquirerIdConfig a SET a.deleted = '1', a.updatedOn = CURRENT_TIMESTAMP WHERE a.id = :id")
    void softDeleteById(Integer id);

}
