package com.bnt.rcp.repository;

import com.bnt.rcp.entity.LocationAtm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface LocationAtmRepository extends JpaRepository<LocationAtm, Integer> {
    
    Optional<LocationAtm> findByLocationId(Integer locationId);
    
    List<LocationAtm> findByRegion(Integer region);
    
    List<LocationAtm> findBySublocation(Boolean sublocation);
    
    List<LocationAtm> findByParentLocationId(Integer parentLocationId);
}