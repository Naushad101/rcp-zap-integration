package com.bnt.rcp.service.impl;

import com.bnt.rcp.dto.LocationDto;
import com.bnt.rcp.entity.Location;
import com.bnt.rcp.entity.LocationAtm;
import com.bnt.rcp.entity.LocationDetail;
import com.bnt.rcp.entity.Merchant;
import com.bnt.rcp.repository.LocationAtmRepository;
import com.bnt.rcp.repository.LocationDetailRepository;
import com.bnt.rcp.repository.LocationRepository;
import com.bnt.rcp.repository.MerchantRepository;
import com.bnt.rcp.service.LocationService;
import com.bnt.rcp.mapper.LocationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private LocationDetailRepository locationDetailRepository;

    @Autowired
    private LocationAtmRepository locationAtmRepository;

    @Autowired
    private MerchantRepository merchantRepository;

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public LocationDto addLocation(LocationDto locationDto) {
   
        Location location = locationMapper.dtoToLocation(locationDto);

        LocationDetail locationDetail = locationMapper.dtoToLocationDetail(locationDto);
        locationDetail.setCountry(null);
        locationDetail.setCountryState(null);
        location.setLocationDetail(locationDetail); 
        locationDetail.setLocation(location);

        System.out.println("Adding location with details: " + locationDto.getMerchantId());
        if (locationDto.getMerchantId() != null) {
            location.setMerchant(merchantRepository.findById(locationDto.getMerchantId()).orElse(null));
        }

       
        location = locationRepository.save(location);

    
        if (locationDto.getRegion() != null) {
            saveLocationAtm(location.getId(), locationDto);
        }

        return locationDto; 
    }

    @Override
    @Transactional(readOnly = true)
    public LocationDto getLocationById(Integer id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isEmpty()) {
            throw new RuntimeException("Location not found with id: " + id);
        }
        
        Location locationEntity = location.get();
        LocationDto locationDto = locationMapper.locationToDto(locationEntity);
        
        Optional<LocationAtm> locationAtm = locationAtmRepository.findByLocationId(id);
        if (locationAtm.isPresent()) {
            locationMapper.locationAtmToDto(locationAtm.get(), locationDto);
        }
        
        return locationDto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocationDto> getAllLocations() {
        List<Location> locations = locationRepository.findAll();
        return locations.stream()
                .map(location -> {
                    LocationDto dto = locationMapper.locationToDto(location);
                 
                    Optional<LocationAtm> locationAtm = locationAtmRepository.findByLocationId(location.getId());
                    if (locationAtm.isPresent()) {
                        locationMapper.locationAtmToDto(locationAtm.get(), dto);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<LocationDto> getLocationsByMerchantId(Integer merchantId) {
        List<Location> locations = locationRepository.findByMerchantId(merchantId);
        return locations.stream()
                .map(location -> {
                    LocationDto dto = locationMapper.locationToDto(location);
                    Optional<LocationAtm> locationAtm = locationAtmRepository.findByLocationId(location.getId());
                    if (locationAtm.isPresent()) {
                        locationMapper.locationAtmToDto(locationAtm.get(), dto);
                    }
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public LocationDto updateLocation(Integer id, LocationDto locationDto) {
        Optional<Location> existingLocationOpt = locationRepository.findById(id);
        if (existingLocationOpt.isEmpty()) {
            throw new RuntimeException("Location not found with id: " + id);
        }
        
        Location existingLocation = existingLocationOpt.get();
        

        if (locationDto.getCode() != null) {
            existingLocation.setCode(locationDto.getCode());
        }
        if (locationDto.getName() != null) {
            existingLocation.setName(locationDto.getName());
        }
        if (locationDto.getDescription() != null) {
            existingLocation.setDescription(locationDto.getDescription());
        }
        if (locationDto.getActivateOn() != null) {
            existingLocation.setActivateOn(locationDto.getActivateOn());
        }
        if (locationDto.getExpiryOn() != null) {
            existingLocation.setExpiryOn(locationDto.getExpiryOn());
        }
        if (locationDto.getPosSafetyFlag() != null) {
            existingLocation.setPosSafetyFlag(locationDto.getPosSafetyFlag());
        }
        if (locationDto.getReversalTimeout() != null) {
            existingLocation.setReversalTimeout(locationDto.getReversalTimeout());
        }
        if (locationDto.getAdditionalAttribute() != null) {
            existingLocation.setAdditionalAttribute(locationDto.getAdditionalAttribute());
        }
        if (locationDto.getLatitude() != null) {
            existingLocation.setLatitude(locationDto.getLatitude());
        }
        if (locationDto.getLongitude() != null) {
            existingLocation.setLongitude(locationDto.getLongitude());
        }
        
   
        if (locationDto.getMerchantId() != null) {
            Optional<Merchant> merchant = merchantRepository.findById(locationDto.getMerchantId());
            existingLocation.setMerchant(merchant.orElse(null));
        }

        if (existingLocation.getLocationDetail() != null) {
            LocationDetail existingDetail = existingLocation.getLocationDetail();
            
            if (locationDto.getAddress1() != null) {
                existingDetail.setAddress1(locationDto.getAddress1());
            }
            if (locationDto.getAddress2() != null) {
                existingDetail.setAddress2(locationDto.getAddress2());
            }
            if (locationDto.getCity() != null) {
                existingDetail.setCity(locationDto.getCity());
            }
            if (locationDto.getZip() != null) {
                existingDetail.setZip(locationDto.getZip());
            }
            if (locationDto.getPhone() != null) {
                existingDetail.setPhone(locationDto.getPhone());
            }
            if (locationDto.getFax() != null) {
                existingDetail.setFax(locationDto.getFax());
            }
            if (locationDto.getWebsite() != null) {
                existingDetail.setWebsite(locationDto.getWebsite());
            }
            if (locationDto.getEmail() != null) {
                existingDetail.setEmail(locationDto.getEmail());
            }
        }
        
        if (locationDto.getRegion() != null) {
            Optional<LocationAtm> existingLocationAtm = locationAtmRepository.findByLocationId(id);
            if (existingLocationAtm.isPresent()) {
                LocationAtm locationAtm = existingLocationAtm.get();
                locationAtm.setRegion(locationDto.getRegion());
                if (locationDto.getSublocation() != null) {
                    locationAtm.setSublocation(locationDto.getSublocation());
                }
                if (locationDto.getParentLocationId() != null) {
                    locationAtm.setParentLocationId(locationDto.getParentLocationId());
                }
                locationAtmRepository.save(locationAtm);
            } else {
                saveLocationAtm(id, locationDto);
            }
        }
        
        Location updatedLocation = locationRepository.save(existingLocation);
        return locationMapper.locationToDto(updatedLocation);
    }

    @Override
    public void deleteLocation(Integer id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isEmpty()) {
            throw new RuntimeException("Location not found with id: " + id);
        }
        
     
        Optional<LocationAtm> locationAtm = locationAtmRepository.findByLocationId(id);
        if (locationAtm.isPresent()) {
            locationAtmRepository.delete(locationAtm.get());
        }
        

        locationRepository.deleteById(id);
    }

    @Override
    public void softDeleteLocation(Integer id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isEmpty()) {
            throw new RuntimeException("Location not found with id: " + id);
        }
        
        Location locationEntity = location.get();
        locationEntity.setDeleted('Y');
        locationRepository.save(locationEntity);
        
 
        Optional<LocationAtm> locationAtm = locationAtmRepository.findByLocationId(id);
        if (locationAtm.isPresent()) {
            LocationAtm atmEntity = locationAtm.get();
            atmEntity.setDeleted('Y');
            locationAtmRepository.save(atmEntity);
        }
    }

    @Override
    public void lockLocation(Integer id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isEmpty()) {
            throw new RuntimeException("Location not found with id: " + id);
        }
        
        Location locationEntity = location.get();
        locationEntity.setLocked('Y');
        locationRepository.save(locationEntity);
        
        Optional<LocationAtm> locationAtm = locationAtmRepository.findByLocationId(id);
        if (locationAtm.isPresent()) {
            LocationAtm atmEntity = locationAtm.get();
            atmEntity.setLocked('Y');
            locationAtmRepository.save(atmEntity);
        }
    }

    @Override
    public void unlockLocation(Integer id) {
        Optional<Location> location = locationRepository.findById(id);
        if (location.isEmpty()) {
            throw new RuntimeException("Location not found with id: " + id);
        }
        
        Location locationEntity = location.get();
        locationEntity.setLocked('N');
        locationRepository.save(locationEntity);
        
        Optional<LocationAtm> locationAtm = locationAtmRepository.findByLocationId(id);
        if (locationAtm.isPresent()) {
            LocationAtm atmEntity = locationAtm.get();
            atmEntity.setLocked('N');
            locationAtmRepository.save(atmEntity);
        }
    }

    public LocationAtm saveLocationAtm(Integer locationId, LocationDto locationDto) {
        LocationAtm locationAtm = locationMapper.dtoToLocationAtm(locationDto);
        locationAtm.setLocationId(locationId); 

        return locationAtmRepository.save(locationAtm);
    }
}