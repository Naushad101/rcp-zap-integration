package com.bnt.rcp.repository;

import com.bnt.rcp.entity.Currency;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

    boolean existsByCode(String code);

    boolean existsByIsoCode(String isoCode);

    @Query("SELECT c FROM Currency c where c.active = '1' and c.deleted = '0'")
    List<Currency> findAllCurrenciesIdAndCode();

    @Modifying
    @Transactional
    @Query("UPDATE Currency c SET c.deleted = '1' , c.updatedOn = CURRENT_TIMESTAMP WHERE c.id = :id")
    void softDeleteById(Integer id);
}
