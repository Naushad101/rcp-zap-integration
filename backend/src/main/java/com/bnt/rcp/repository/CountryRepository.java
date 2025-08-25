package com.bnt.rcp.repository;

import com.bnt.rcp.entity.Country;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends JpaRepository<Country, Integer> {

    boolean existsByCode(String code);

    boolean existsByIsoCode(String isoCode);

    @Query("SELECT c FROM Country c WHERE c.active = '1' AND c.deleted = '0'")
    List<Country> findAllCountriesIdAndName();

    @Modifying
    @Transactional
    @Query("UPDATE Country c SET c.deleted = '1' , c.updatedOn = CURRENT_TIMESTAMP WHERE c.id = :id")
    void softDeleteById(Integer id);

}
