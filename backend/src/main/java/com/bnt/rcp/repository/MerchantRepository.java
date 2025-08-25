package com.bnt.rcp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bnt.rcp.entity.Merchant;

import jakarta.transaction.Transactional;


public interface MerchantRepository extends JpaRepository<Merchant, Integer> {

    boolean existsByName(String name);

    @Query("SELECT m FROM Merchant m WHERE m.locked = '1' AND m.deleted = '0'")
    List<Merchant> findAllMerchantsIdAndCode();

    @Transactional
    @Modifying
    @Query("UPDATE Merchant m SET m.deleted = '1', m.updatedOn = CURRENT_TIMESTAMP WHERE m.id = :id")
    void softDeleteById(Integer id);

    @Query(value = "SELECT id, code FROM merchant_category_code", nativeQuery = true)
    List<Object[]> findAllMerchantCategoryCodes();

}
