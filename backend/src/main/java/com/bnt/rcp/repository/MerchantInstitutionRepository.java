package com.bnt.rcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bnt.rcp.entity.MerchantInstitution;

import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface MerchantInstitutionRepository extends JpaRepository<MerchantInstitution, Integer> {

    boolean existsByName(String name);
    
    @Query("SELECT m FROM MerchantInstitution m WHERE m.locked = '1' AND m.deleted = '0'")
    List<MerchantInstitution> findAllMerchantInstitutionsIdAndName();

    @Transactional
    @Modifying
    @Query("UPDATE MerchantInstitution m SET m.deleted = '1', m.updatedOn = CURRENT_TIMESTAMP WHERE m.id = :id")
    void softDeleteById(Integer id);
    
}