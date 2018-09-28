package com.ifrabbit.nk.redisCache;

import com.ifrabbit.nk.constant.RedisConstant;
import com.ifrabbit.nk.redisService.RedisInitService;
import com.ifrabbit.nk.redisService.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cache")
public class RedisCacheController {
    @Autowired
    private RedisService redisService;

    @Autowired
    private RedisInitService redisInitService;


    @RequestMapping("clear")
    @ResponseBody
    public String flushAll(){
        redisService.flushAll();

        return "ok";
    }

    @RequestMapping("reLoad")
    @ResponseBody
    public String reLoad(){
        redisService.flushAll();
        redisInitService.init();
        return "ok";
    }

    @RequestMapping("getUserReport")
    @ResponseBody
    public Object getUserReport(@RequestParam("code")String code){
        return redisService.get(RedisConstant.USER_REPORT_BY_CODE_+code);
    }

    @RequestMapping("getCompany")
    @ResponseBody
    public Object getCompany(@RequestParam("id")String id){
        return redisService.get(RedisConstant.COMPANY_BY_ID_+id);
    }

}
