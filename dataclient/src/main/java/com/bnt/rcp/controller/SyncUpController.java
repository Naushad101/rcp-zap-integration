package com.bnt.rcp.controller;

import org.springframework.web.bind.annotation.RestController;

import com.bnt.rcp.service.SyncUpServiceImpl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@RestController
@RequestMapping("/syncup")
@EnableScheduling
@Slf4j
public class SyncUpController {

    private final SyncUpServiceImpl syncUpServiceImpl;

    public SyncUpController(SyncUpServiceImpl syncUpServiceImpl) {
        this.syncUpServiceImpl = syncUpServiceImpl;
    }

    @GetMapping("/execute")
    @Scheduled(fixedDelayString = "${schedular.time}")
    public void startSceduler() {
        log.info("Starting scheduled sync up process");
        try {
            syncUpServiceImpl.executeSyncUp();
            log.info("Scheduled sync up process completed successfully");
        } catch (Exception e) {
            log.error("Error occurred during scheduled sync up process: {}", e.getMessage(), e);
        }
    }

}
