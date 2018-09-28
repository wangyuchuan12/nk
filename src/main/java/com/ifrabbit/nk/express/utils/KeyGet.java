package com.ifrabbit.nk.express.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class KeyGet {

    public static String setKey(Integer num) {
        String n = "-" + ((int) (Math.random() * 900000) + 100000);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dd = "-" + sdf.format(d);
        String id;
        switch (num) {
            case 0:
                id = "QSWS" + dd + n;
                return id;
            case 1:
                id = "PS" + dd + n;
                return id;
            case 2:
                id = "GDZ" + dd + n;
                return id;
            case 3:
                id = "TH" + dd + n;
                return id;
            case 4:
                id = "CD" + dd + n;
                return id;
        }
        return null;
    }
}
