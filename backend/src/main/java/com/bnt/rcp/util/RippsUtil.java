package com.bnt.rcp.util;

import java.sql.Timestamp;
import java.util.UUID;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.DateTimePath;

public class RippsUtil {

    private RippsUtil() {
        
    }
    public static String generateMerchantInstitutionCode(){
        return "MID" + UUID.randomUUID().toString().replace("-", "").substring(0, 11).toUpperCase();
    }

    public static Predicate getTimestampPredicate(DateTimePath<Timestamp> createdOn, DateTimePath<Timestamp> updatedOn,
            String from, String to) {
        long fromMilliseconds = Long.parseLong(from) * 1000;
        long toMilliseconds = Long.parseLong(to) * 1000;

        Timestamp fromDate = new Timestamp(fromMilliseconds);
        Timestamp toDate = new Timestamp(toMilliseconds);

        return createdOn.between(fromDate, toDate).or(updatedOn.between(fromDate, toDate));
    }

}
