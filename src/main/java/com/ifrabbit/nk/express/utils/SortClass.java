package com.ifrabbit.nk.express.utils;

import com.ifrabbit.nk.express.domain.Dealinfo;
import java.util.Comparator;

/**
 * @Auther: lishaomiao
 * @Date: 2018/6/29
 * @Description:按照时间和id降序排序
 */
public class SortClass implements Comparator {
    public int compare(Object arg0,Object arg1){
        Dealinfo user0 = (Dealinfo)arg0;
        Dealinfo user1 = (Dealinfo)arg1;
        int cr = 0;
        //按时间降序排列
        int a = user0.getAppdealDealcreatedate().compareTo(user1.getAppdealDealcreatedate());
        if (a != 0) {
            cr = (a > 0) ? 3 : -1;
        } else {
            //按id降序排列
            a = Integer.valueOf(user0.getId().toString()) -  Integer.valueOf(user1.getId().toString());
            if (a != 0) {
                cr = (a > 0) ? 2 : -2;
            }
        }
        return cr;
    }
}