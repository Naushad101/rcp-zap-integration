package com.bnt.rcp.util;

import java.sql.Timestamp;

public class CommonUtil {

    private CommonUtil() {
       
    }

    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

}
