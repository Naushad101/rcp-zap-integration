package com.bnt.rcp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bnt.rcp.entity.Institution;

@Repository
public interface InstitutionRepository extends JpaRepository<Institution, Integer>{

}
