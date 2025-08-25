package com.bnt.rcp.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bnt.rcp.entity.SyncUpEntity;

@Repository
public interface SyncUpRepository extends JpaRepository<SyncUpEntity, Integer> {

    @Query(value = "SELECT MAX(end_time) FROM database_sync_up WHERE status = :status", nativeQuery = true)
    public Timestamp getLastEndTime(String status);
}
