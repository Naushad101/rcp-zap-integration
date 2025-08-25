package com.bnt.rcp.repository;

import com.bnt.rcp.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
    
    @Query("SELECT l FROM Location l WHERE l.merchant.id = :merchantId")
    List<Location> findByMerchantId(@Param("merchantId") Integer merchantId);
    

    @Query("SELECT l FROM Location l WHERE l.deleted = 'N' OR l.deleted IS NULL")
    List<Location> findAllActive();
    

    @Query("SELECT l FROM Location l WHERE LOWER(l.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Location> findByNameContainingIgnoreCase(@Param("name") String name);
    

    List<Location> findByCode(String code);
    

    @Query("SELECT l FROM Location l WHERE l.locked = 'N' OR l.locked IS NULL")
    List<Location> findAllUnlocked();
}