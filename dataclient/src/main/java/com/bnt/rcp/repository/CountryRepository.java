package com.bnt.rcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnt.rcp.entity.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {

}
