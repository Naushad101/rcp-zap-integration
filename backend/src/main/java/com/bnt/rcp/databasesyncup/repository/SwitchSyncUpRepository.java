package com.bnt.rcp.databasesyncup.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.bnt.rcp.databasesyncup.dto.FilterDataDto;
import com.bnt.rcp.entity.*;
import com.bnt.rcp.util.RippsUtil;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
public class SwitchSyncUpRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Currency> getCurrencyData(FilterDataDto filterDataDto) {
        QCurrency currency = QCurrency.currency;

        JPAQuery<Currency> jpaQuery = new JPAQuery<>(entityManager);

        if (filterDataDto == null) {
            jpaQuery.from(currency);
        } else {
            Predicate date = RippsUtil.getTimestampPredicate(currency.createdOn, currency.updatedOn,
                    filterDataDto.getFromDate(),
                    filterDataDto.getToDate());
            jpaQuery.from(currency)
                   .where(date)
                   .orderBy(currency.updatedOn.asc());
        }
        return jpaQuery.fetch();
    }

    public List<Country> getCountryData(FilterDataDto filterDataDto) {
        QCountry country = QCountry.country;

        JPAQuery<Country> jpaQuery = new JPAQuery<>(entityManager);

        if (filterDataDto == null) {
            jpaQuery.from(country);
        } else {
            Predicate date = RippsUtil.getTimestampPredicate(country.createdOn, country.updatedOn,
                    filterDataDto.getFromDate(),
                    filterDataDto.getToDate());
            jpaQuery.from(country)
                   .where(date)
                   .orderBy(country.updatedOn.asc());
        }
        return jpaQuery.fetch();
    }

    public List<CountryState> getCountryStateData(FilterDataDto filterDataDto) {
        QCountryState countryState = QCountryState.countryState;

        JPAQuery<CountryState> jpaQuery = new JPAQuery<>(entityManager);

        if (filterDataDto == null) {
            jpaQuery.from(countryState);
        } else {
            Predicate date = RippsUtil.getTimestampPredicate(countryState.createdOn, countryState.updatedOn,
                    filterDataDto.getFromDate(),
                    filterDataDto.getToDate());
            jpaQuery.from(countryState)
                   .where(date)
                   .orderBy(countryState.updatedOn.asc());
        }
        return jpaQuery.fetch();
    }

    public List<AcquirerIdConfig> getAcquirerIdConfigData(FilterDataDto filterDataDto) {
        QAcquirerIdConfig acquirerIdConfig = QAcquirerIdConfig.acquirerIdConfig;

        JPAQuery<AcquirerIdConfig> jpaQuery = new JPAQuery<>(entityManager);

        if (filterDataDto == null) {
            jpaQuery.from(acquirerIdConfig);
        } else {
            Predicate date = RippsUtil.getTimestampPredicate(acquirerIdConfig.createdOn, acquirerIdConfig.updatedOn,
                    filterDataDto.getFromDate(),
                    filterDataDto.getToDate());
            jpaQuery.from(acquirerIdConfig)
                   .where(date)
                   .orderBy(acquirerIdConfig.updatedOn.asc());
        }
        return jpaQuery.fetch();
    }

}
