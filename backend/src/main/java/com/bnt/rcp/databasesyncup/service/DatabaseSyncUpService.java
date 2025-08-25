package com.bnt.rcp.databasesyncup.service;

import com.bnt.rcp.databasesyncup.dto.DatabaseSyncUpDto;
import com.bnt.rcp.databasesyncup.dto.FilterDataDto;

public interface DatabaseSyncUpService {

    DatabaseSyncUpDto getDataFromAtm(FilterDataDto filterDataDto);

    DatabaseSyncUpDto getDataFromSwitch(FilterDataDto filterDataDto);

}
