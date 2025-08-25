package com.bnt.rcp.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bnt.rcp.constant.CommonConstant;
import com.bnt.rcp.dto.DataClientResponse;
import com.bnt.rcp.dto.DatabaseSyncUpDto;
import com.bnt.rcp.entity.SyncUpEntity;
import com.bnt.rcp.repository.SyncUpRepository;
import com.bnt.rcp.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SyncUpServiceImpl {

    private final SyncUpRepository syncUpRepository;

    private final AtmServiceImpl atmServiceImpl;

    private final SwitchServiceImpl switchServiceImpl;

    private final RestTemplate restTemplate;

    @Value("${component.name}")
    private String component;

    @Value("${backend.url}")
    private String backendUrl;

    public SyncUpServiceImpl(SyncUpRepository syncUpRepository, AtmServiceImpl atmServiceImpl,
            SwitchServiceImpl switchServiceImpl,
            RestTemplate restTemplate) {
        this.syncUpRepository = syncUpRepository;
        this.atmServiceImpl = atmServiceImpl;
        this.switchServiceImpl = switchServiceImpl;
        this.restTemplate = restTemplate;
    }

    public void executeSyncUp() {
        log.info("Starting sync up process");
        Timestamp startTimestamp = CommonUtil.getCurrentTimestamp();

        Timestamp endTimestamp = syncUpRepository.getLastEndTime(CommonConstant.SUCCESS);

        String url = prepareURL(endTimestamp, startTimestamp, component);

        DataClientResponse response = restTemplate.getForObject(url, DataClientResponse.class);
        log.info("Received response {} from backend service", response);

        if (response == null || response.getData() == null) {
            log.error("No data received from backend service for URL: {}", url);
            return;
        }
        DatabaseSyncUpDto syncUpDto = response.getData();

        saveData(syncUpDto, startTimestamp, endTimestamp);
        log.info("Sync up process completed");
    }

    private void saveData(DatabaseSyncUpDto syncUpDto, Timestamp startTime, Timestamp endTime) {
        log.info("Processing sync up data for component: {}", component);

        if (component.equalsIgnoreCase("ATM")) {
            atmServiceImpl.execute(syncUpDto);
            saveSyncUpEntity(atmServiceImpl.getFailedData().toString(), atmServiceImpl.getSuccessData().toString(),
                    startTime,
                    endTime);
            log.info("ATM component data processing completed");
        } else if (component.equalsIgnoreCase("HSM")) {
            log.info("HSM component processing not implemented yet");
        } else if (component.equalsIgnoreCase("SWITCH")) {
            switchServiceImpl.execute(syncUpDto);
            saveSyncUpEntity(switchServiceImpl.getFailedData().toString(),
                    switchServiceImpl.getSuccessData().toString(), startTime,
                    endTime);
            log.info("SWITCH component data processing completed");
        } else {
            log.error("Unknown component type: {}", component);
        }
    }

    private void saveSyncUpEntity(String failed, String success, Timestamp startTime, Timestamp endTime) {
        if (failed.equalsIgnoreCase("")) {
            if (success.equalsIgnoreCase("")) {
                log.info("No data found for sync up");
            } else {
                saveSyncUp(endTime, startTime, CommonConstant.SUCCESS, success);
                log.info("Saved successful sync up entity");
            }
        } else {
            saveSyncUp(endTime, startTime, CommonConstant.ERROR, success);
            log.error("Saved sync up entity with errors: {}", failed);
        }
    }

    private void saveSyncUp(Timestamp startTime, Timestamp endTime, String status, String reason) {
        log.info("Saving sync up entity with status: {}, reason: {}", status, reason);
        SyncUpEntity syncUpEntity = new SyncUpEntity();
        if (startTime == null)
            syncUpEntity.setStartTime(null);
        else
            syncUpEntity.setStartTime(startTime.toString());
        syncUpEntity.setEndTime(endTime.toString());
        syncUpEntity.setStatus(status);
        syncUpEntity.setReason(reason);
        syncUpRepository.save(syncUpEntity);
        log.info("Sync up entity saved successfully");
    }

    private String prepareURL(Timestamp startTime, Timestamp endTime, String component) {
        String restUrl = null;
        String filter = null;

        if (startTime != null) {
            Long startTimeLong = startTime.getTime() / 1000;
            Long endTimeLong = endTime.getTime() / 1000;
            filter = "?period=" + startTimeLong + "-" + endTimeLong;
        } else {
            filter = "?period=";
        }

        if (component.equalsIgnoreCase("")) {
            restUrl = backendUrl + filter;
        } else {
            restUrl = backendUrl + "/" + component + filter;
        }

        log.info("Prepared URL for component {}: {}", component, restUrl);
        return restUrl;
    }

}
