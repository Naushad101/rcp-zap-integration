package com.bnt.rcp.service;

import com.bnt.rcp.dto.LocationDto;

import java.util.List;

public interface LocationService {
    
    LocationDto addLocation(LocationDto locationDto);
    
    LocationDto getLocationById(Integer id);
    
    List<LocationDto> getAllLocations();
    
    List<LocationDto> getLocationsByMerchantId(Integer merchantId);
    
    LocationDto updateLocation(Integer id, LocationDto locationDto);
    
    void deleteLocation(Integer id);
    
    void softDeleteLocation(Integer id);
    
    void lockLocation(Integer id);
    
    void unlockLocation(Integer id);
}