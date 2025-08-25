package com.bnt.rcp.repository;

import com.bnt.rcp.entity.CountryState;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryStateRepository extends JpaRepository<CountryState, Integer> {

    boolean existsByCode(String code);

    @Query("SELECT s FROM CountryState s WHERE s.active = '1' AND s.deleted = '0'")
    List<CountryState> findAllStatesIdAndName();

    @Modifying
    @Transactional
    @Query("UPDATE CountryState s SET s.deleted = '1' , s.updatedOn = CURRENT_TIMESTAMP WHERE s.id = :id")
    void softDeleteById(Integer id);
}
