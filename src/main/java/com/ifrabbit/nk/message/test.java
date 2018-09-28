package com.ifrabbit.nk.message;

import com.bstek.uflo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/7/5
 * Time:10:57
 */
public class test {
    @Autowired
    static TaskService taskService;
    public static void main(String[] args) {
        taskService.complete(187001);
    }
}
