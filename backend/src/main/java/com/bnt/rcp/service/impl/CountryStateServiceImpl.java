package com.bnt.rcp.service.impl;

import com.bnt.rcp.constant.RippsRestConstant;
import com.bnt.rcp.dto.CountryStateDto;
import com.bnt.rcp.entity.Country;
import com.bnt.rcp.entity.CountryState;
import com.bnt.rcp.exception.RippsAdminRestException;
import com.bnt.rcp.mapper.CountryStateMapper;
import com.bnt.rcp.repository.CountryRepository;
import com.bnt.rcp.repository.CountryStateRepository;
import com.bnt.rcp.service.CountryStateService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CountryStateServiceImpl implements CountryStateService {

    private final CountryStateRepository countryStateRepository;
    private final CountryRepository countryRepository;

    public CountryStateServiceImpl(CountryStateRepository countryStateRepository, CountryRepository countryRepository) {
        this.countryStateRepository = countryStateRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public Integer createCountryState(CountryStateDto countryStateDto) {
        log.info("Creating countryState with details: {}", countryStateDto);

        Optional<Country> existingCountryOpt = countryRepository.findById(countryStateDto.getCountryId());

        if (existingCountryOpt.isEmpty()) {
            log.error("Country not found with id: {}", countryStateDto.getCountryId());
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "Country not found with id: " + countryStateDto.getCountryId(), null);
        }

        Country country = existingCountryOpt.get();

        if (countryStateRepository.existsByCode(countryStateDto.getCode())) {
            log.error("CountryState code already exists: {}", countryStateDto.getCode());
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "CountryState code already exists", null);
        }

        CountryState state = CountryStateMapper.INSTANCE.toEntity(countryStateDto);
        state.setCountry(country);

        CountryState createdState = countryStateRepository.save(state);
        log.info("Successfully created countryState with id: {}", createdState.getId());

        return createdState.getId();
    }

    @Override
    public List<CountryStateDto> getAllCountryStates() {
        log.info("Fetching all countryStates");

        List<CountryState> countryStateList = countryStateRepository.findAll().stream()
            .filter(state -> state.getDeleted() != '1')
            .toList();

       List<CountryStateDto> countryStateDtoList = CountryStateMapper.INSTANCE.toDtoList(countryStateList);

        log.info("Successfully fetched all {} countryStates", countryStateDtoList.size());
        return countryStateDtoList;
    }

    @Override
    public List<CountryStateDto> getAllCountryStatesIdAndName() {
        log.info("Fetching all countryStates with id and Name");

        List<CountryState> countryStates = countryStateRepository.findAllStatesIdAndName();
        List<CountryStateDto> countryStateDtoList = CountryStateMapper.INSTANCE.toDtoList(countryStates);

        log.info("Successfully fetched all {} countryStates with id and Name", countryStateDtoList.size());
        return countryStateDtoList;
    }

    @Override
    public Integer updateCountryState(Integer id, CountryStateDto countryStateDto) {
        log.info("Updating CountryState with id: {}", id);

        Optional<CountryState> existingStateOpt = countryStateRepository.findById(id);

        if (existingStateOpt.isEmpty()) {
            log.warn("CountryState with id: {} not found", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "CountryState not found with id: " + id, null);
        }

        CountryState existingState = existingStateOpt.get();
        existingState.setActive(countryStateDto.getActive());

        CountryState updatedState = countryStateRepository.save(existingState);
        log.info("Successfully updated CountryState with id: {}", updatedState.getId());

        return updatedState.getId();
    }

    @Override
    public void deleteCountryState(Integer id) {
        log.info("Soft deleting CountryState with id: {}", id);

        CountryState existingCountryState = countryStateRepository.findById(id).orElse(null);
        if (existingCountryState == null) {
            log.warn("CountryState with id: {} not found", id);
            throw new RippsAdminRestException(RippsRestConstant.ERROR, "CountryState not found with id: " + id, null);
        }

        countryStateRepository.softDeleteById(id);
        log.info("Successfully soft deleted CountryState with id: {}", id);
    }
}
