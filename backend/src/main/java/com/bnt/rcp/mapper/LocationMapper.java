package com.bnt.rcp.mapper;

import com.bnt.rcp.dto.LocationDto;
import com.bnt.rcp.entity.Country;
import com.bnt.rcp.entity.CountryState;
import com.bnt.rcp.entity.Location;
import com.bnt.rcp.entity.LocationAtm;
import com.bnt.rcp.entity.LocationDetail;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

  
    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "code", source = "code"),
        @Mapping(target = "name", source = "name"),
        @Mapping(target = "description", source = "description"),
        @Mapping(target = "activateOn", source = "activateOn"),
        @Mapping(target = "expiryOn", source = "expiryOn"),
        @Mapping(target = "additionalAttribute", source = "additionalAttribute"),
        // @Mapping(target = "totalDevice", ignore = true), 
        @Mapping(target = "merchant", ignore = true), 
        @Mapping(target = "latitude", source = "latitude"),
        @Mapping(target = "longitude", source = "longitude"),
        @Mapping(target = "posSafetyFlag", source = "posSafetyFlag"),
        @Mapping(target = "reversalTimeout", source = "reversalTimeout"),
        @Mapping(target = "deleted", source = "deleted"),
        @Mapping(target = "locked", source = "locked"),
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "updatedBy", ignore = true),
        @Mapping(target = "createdOn", ignore = true),
        @Mapping(target = "updatedOn", ignore = true),
        @Mapping(target = "locationDetail", ignore = true) 
    })
    Location dtoToLocation(LocationDto dto);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "address1", source = "address1"),
        @Mapping(target = "address2", source = "address2"),
        @Mapping(target = "city", source = "city"),
        @Mapping(target = "zip", source = "zip"),
        @Mapping(target = "phone", source = "phone"),
        @Mapping(target = "fax", source = "fax"),
        @Mapping(target = "website", source = "website"),
        @Mapping(target = "email", source = "email"),
        @Mapping(target = "countryState", source = "stateName", qualifiedByName = "mapCountryState"),
        @Mapping(target = "country", source = "countryId", qualifiedByName = "mapCountry"),
        @Mapping(target = "createdBy", ignore = true),
        @Mapping(target = "updatedBy", ignore = true),
        @Mapping(target = "createdOn", ignore = true),
        @Mapping(target = "updatedOn", ignore = true),
        @Mapping(target = "location", ignore = true) 
    })
    LocationDetail dtoToLocationDetail(LocationDto dto);

    @Mappings({
        @Mapping(target = "id", ignore = true), 
        @Mapping(target = "region", source = "region"),
        @Mapping(target = "deleted", source = "atmDeleted"),
        @Mapping(target = "locked", source = "atmLocked"),
        @Mapping(target = "sublocation", source = "sublocation"),
        @Mapping(target = "parentLocationId", source = "parentLocationId"),
        @Mapping(target = "locationId", ignore = true),
        @Mapping(target = "createdBy", ignore = true), 
        @Mapping(target = "updatedBy", ignore = true),
        @Mapping(target = "createdOn", ignore = true),
        @Mapping(target = "updatedOn", ignore = true)
    })
    LocationAtm dtoToLocationAtm(LocationDto dto);

 
    @Mappings({
        @Mapping(source = "id", target = "id"),
        @Mapping(source = "code", target = "code"),
        @Mapping(source = "name", target = "name"),
        @Mapping(source = "description", target = "description"),
        @Mapping(source = "activateOn", target = "activateOn"),
        @Mapping(source = "expiryOn", target = "expiryOn"),
        @Mapping(source = "additionalAttribute", target = "additionalAttribute"),
        @Mapping(source = "merchant.id", target = "merchantId"),
        @Mapping(source = "merchant.name", target = "merchantName"),
        @Mapping(source = "merchant.code", target = "merchantCode"),
        // @Mapping(source = "storeId", target = "storeId"),
        @Mapping(source = "latitude", target = "latitude"),
        @Mapping(source = "longitude", target = "longitude"),
        @Mapping(source = "posSafetyFlag", target = "posSafetyFlag"),
        @Mapping(source = "reversalTimeout", target = "reversalTimeout"),
        @Mapping(source = "deleted", target = "deleted"),
        @Mapping(source = "locked", target = "locked"),
        @Mapping(source = "id", target = "locationId"),

        @Mapping(source = "locationDetail.id", target = "locationDetailId"),
        @Mapping(source = "locationDetail.address1", target = "address1"),
        @Mapping(source = "locationDetail.address2", target = "address2"),
        @Mapping(source = "locationDetail.city", target = "city"),
        @Mapping(source = "locationDetail.zip", target = "zip"),
        @Mapping(source = "locationDetail.phone", target = "phone"),
        @Mapping(source = "locationDetail.fax", target = "fax"),
        @Mapping(source = "locationDetail.website", target = "website"),
        @Mapping(source = "locationDetail.email", target = "email"),
        @Mapping(source = "locationDetail.country.id", target = "countryId"),
        @Mapping(source = "locationDetail.country.countryName", target = "countryName"),
        @Mapping(source = "locationDetail.country.code", target = "countryCode"),
        @Mapping(source = "locationDetail.countryState.id", target = "stateId"),
        @Mapping(source = "locationDetail.countryState.stateName", target = "stateName"),
        @Mapping(source = "locationDetail.countryState.code", target = "stateCode"),

        @Mapping(target = "region", ignore = true),
        @Mapping(target = "atmDeleted", ignore = true),
        @Mapping(target = "atmLocked", ignore = true),
        @Mapping(target = "sublocation", ignore = true),
        @Mapping(target = "parentLocationId", ignore = true)
    })
    LocationDto locationToDto(Location location);

    
    @Mappings({
        @Mapping(source = "region", target = "region"),
        @Mapping(source = "deleted", target = "atmDeleted"),
        @Mapping(source = "locked", target = "atmLocked"),
        @Mapping(source = "sublocation", target = "sublocation"),
        @Mapping(source = "parentLocationId", target = "parentLocationId"),
     
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "code", ignore = true),
        @Mapping(target = "name", ignore = true),
        @Mapping(target = "description", ignore = true),
        @Mapping(target = "activateOn", ignore = true),
        @Mapping(target = "expiryOn", ignore = true),
        @Mapping(target = "additionalAttribute", ignore = true),
        @Mapping(target = "merchantId", ignore = true),
        @Mapping(target = "merchantName", ignore = true),
        @Mapping(target = "merchantCode", ignore = true),
        // @Mapping(target = "storeId", ignore = true),
        @Mapping(target = "latitude", ignore = true),
        @Mapping(target = "longitude", ignore = true),
        @Mapping(target = "posSafetyFlag", ignore = true),
        @Mapping(target = "reversalTimeout", ignore = true),
        @Mapping(target = "deleted", ignore = true),
        @Mapping(target = "locked", ignore = true),
        @Mapping(target = "locationId", ignore = true),
        @Mapping(target = "locationDetailId", ignore = true),
        @Mapping(target = "address1", ignore = true),
        @Mapping(target = "address2", ignore = true),
        @Mapping(target = "city", ignore = true),
        @Mapping(target = "zip", ignore = true),
        @Mapping(target = "phone", ignore = true),
        @Mapping(target = "fax", ignore = true),
        @Mapping(target = "website", ignore = true),
        @Mapping(target = "email", ignore = true),
        @Mapping(target = "countryId", ignore = true),
        @Mapping(target = "countryName", ignore = true),
        @Mapping(target = "countryCode", ignore = true),
        @Mapping(target = "stateId", ignore = true),
        @Mapping(target = "stateName", ignore = true),
        @Mapping(target = "stateCode", ignore = true)
    })
    void locationAtmToDto(LocationAtm locationAtm, @MappingTarget LocationDto locationDto);

 
    @Mappings({
        @Mapping(source = "id", target = "locationDetailId"),
        @Mapping(source = "address1", target = "address1"),
        @Mapping(source = "address2", target = "address2"),
        @Mapping(source = "city", target = "city"),
        @Mapping(source = "zip", target = "zip"),
        @Mapping(source = "phone", target = "phone"),
        @Mapping(source = "fax", target = "fax"),
        @Mapping(source = "website", target = "website"),
        @Mapping(source = "email", target = "email"),
        @Mapping(source = "country.id", target = "countryId"),
        @Mapping(source = "country.countryName", target = "countryName"),
        @Mapping(source = "country.code", target = "countryCode"),
        @Mapping(source = "countryState.id", target = "stateId"),
        @Mapping(source = "countryState.stateName", target = "stateName"),
        @Mapping(source = "country.code", target = "stateCode"),
        @Mapping(source = "location.id", target = "locationId"),
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "code", ignore = true),
        @Mapping(target = "name", ignore = true),
        @Mapping(target = "description", ignore = true),
        @Mapping(target = "activateOn", ignore = true),
        @Mapping(target = "expiryOn", ignore = true),
        @Mapping(target = "additionalAttribute", ignore = true),
        @Mapping(target = "merchantId", ignore = true),
        @Mapping(target = "merchantName", ignore = true),
        @Mapping(target = "merchantCode", ignore = true),
        // @Mapping(target = "storeId", ignore = true),
        @Mapping(target = "latitude", ignore = true),
        @Mapping(target = "longitude", ignore = true),
        @Mapping(target = "posSafetyFlag", ignore = true),
        @Mapping(target = "reversalTimeout", ignore = true),
        @Mapping(target = "deleted", ignore = true),
        @Mapping(target = "locked", ignore = true),
        @Mapping(target = "region", ignore = true),
        @Mapping(target = "atmDeleted", ignore = true),
        @Mapping(target = "atmLocked", ignore = true),
        @Mapping(target = "sublocation", ignore = true),
        @Mapping(target = "parentLocationId", ignore = true)
    })
    void locationDetailToDto(LocationDetail locationDetail, @MappingTarget LocationDto locationDto);

    @AfterMapping
    default void afterMapping(LocationDto dto, @MappingTarget Location location, @MappingTarget LocationDetail locationDetail, @MappingTarget LocationAtm locationAtm) {
        if (locationDetail != null && location != null) {
            locationDetail.setLocation(location);
            location.setLocationDetail(locationDetail);
        }
        if (locationAtm != null && locationAtm.getSublocation() != null && locationAtm.getSublocation()) {
            locationAtm.setLocationId(location != null ? location.getId() : null);
        }
    }

    @Named("mapCountryState")
    default CountryState mapCountryState(String stateName) {
        if (stateName == null) return null;
       
        CountryState countryState = new CountryState();
        countryState.setId(1); 
        countryState.setStateName(stateName);
        return countryState;
    }

    @Named("mapCountry")
    default Country mapCountry(Integer countryId) {
        if (countryId == null) return null;
    
        Country country = new Country();
        country.setId(countryId);
        return country;
    }
}