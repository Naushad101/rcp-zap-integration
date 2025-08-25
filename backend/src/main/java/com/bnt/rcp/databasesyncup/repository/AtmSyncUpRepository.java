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
public class AtmSyncUpRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<MerchantInstitution> getMerchantInstitutionData(FilterDataDto filterDataDto) {
        QMerchantInstitution merchantIns = QMerchantInstitution.merchantInstitution;
        JPAQuery<MerchantInstitution> jpaQuery = new JPAQuery<>(entityManager);

        if (filterDataDto == null) {
            jpaQuery.from(merchantIns);
        } else {
            Predicate date = RippsUtil.getTimestampPredicate(merchantIns.createdOn, merchantIns.updatedOn,
                    filterDataDto.getFromDate(),
                    filterDataDto.getToDate());
            jpaQuery.from(merchantIns)
                   .where(date)
                   .orderBy(merchantIns.updatedOn.asc());
        }

        return jpaQuery.fetch();
    }

}
