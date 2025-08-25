package com.bnt.rcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bnt.rcp.entity.Currency;

public interface CurrencyRepository extends JpaRepository<Currency, Integer>{

}
