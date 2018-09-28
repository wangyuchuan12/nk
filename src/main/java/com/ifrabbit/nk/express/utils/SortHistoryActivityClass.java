package com.ifrabbit.nk.express.utils;

import com.bstek.uflo.model.HistoryActivity;

import java.util.Comparator;

/**
 * @Auther: lishaomiao
 * @Date: 2018/6/29
 * @Description:按照时间和id降序排序
 */
public class SortHistoryActivityClass implements Comparator {
    public int compare(Object arg0,Object arg1){
        HistoryActivity user0 = (HistoryActivity)arg0;
        HistoryActivity user1 = (HistoryActivity)arg1;
        int cr = 0;
        //按时间降序排列
        int a = user0.getCreateDate().compareTo(user1.getCreateDate());
        if (a != 0) {
            cr = (a > 0) ? 3 : -1;
        } else {
            //按id降序排列
            a = (int) user0.getId() - (int)user1.getId();
            if (a != 0) {
                cr = (a > 0) ? 2 : -2;
            }
        }
        return cr;
    }
}
