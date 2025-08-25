package com.bnt.rcp.databasesyncup.service.impl;

import org.springframework.stereotype.Service;

import com.bnt.rcp.databasesyncup.dto.*;
import com.bnt.rcp.databasesyncup.service.DatabaseSyncUpService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DatabaseSyncUpServiceImpl implements DatabaseSyncUpService {

    private final AtmSyncUpServiceImpl atmSyncUpServiceImpl;

    private final SwitchSyncUpServiceImpl switchSyncUpServiceImpl;

    public DatabaseSyncUpServiceImpl(AtmSyncUpServiceImpl atmSyncUpServiceImpl, SwitchSyncUpServiceImpl switchSyncUpServiceImpl) {
        this.atmSyncUpServiceImpl = atmSyncUpServiceImpl;
        this.switchSyncUpServiceImpl = switchSyncUpServiceImpl;
    }

    @Override
    public DatabaseSyncUpDto getDataFromAtm(FilterDataDto filterDataDto) {
        log.info("Fetching data from ATM");
        DatabaseSyncUpDto databaseSyncUpDto = atmSyncUpServiceImpl.getDataFromAtm(filterDataDto);
        log.info("Successfully fetched data from ATM");
        return databaseSyncUpDto;
    }

    @Override
    public DatabaseSyncUpDto getDataFromSwitch(FilterDataDto filterDataDto) {
        log.info("Fetching data from Switch");
        DatabaseSyncUpDto databaseSyncUpDto = switchSyncUpServiceImpl.getDataFromSwitch(filterDataDto);
        log.info("Successfully fetched data from Switch");
        return databaseSyncUpDto;
    }

}
