package com.ifrabbit.nk.redisService;

import com.ifrabbit.nk.constant.RedisConstant;
import com.ifrabbit.nk.usercenter.domain.UserReport;
import org.apache.catalina.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class UserReportRedisService {

    @Autowired
    private RedisService redisService;

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(UserReportRedisService.class);

    //增加findOne缓存和在findAll缓存中添加元素
    public void doCache(UserReport userReport){
        //this.doFindOneCache(userReport);
        this.doFindOneByCodeCache(userReport);
        //this.doSaveFindAllCache(userReport);
    }

    public boolean isInit(){
        Boolean isInit = redisService.get(RedisConstant.USER_REPORT_IS_INIT);
        redisService.set(RedisConstant.USER_REPORT_IS_INIT,true);

        if(isInit==null){
            return false;
        }else{
            return isInit;
        }

    }

    //新增findOneByCode缓存
    public void doFindOneByCodeCache(UserReport userReport){
        logger.debug("设置userReport.findOneByCode缓存：{}",userReport);
        redisService.set(RedisConstant.USER_REPORT_BY_CODE_+userReport.getCode(),userReport);
    }

    //根据code取缓存数据
    public UserReport findOneByCodeCache(String code){

        UserReport userReport =  redisService.get(RedisConstant.USER_REPORT_BY_CODE_+code);
        logger.debug("根据code{}从缓存中获取userReport{}",code,userReport);
        return userReport;
    }

    //新增findOne缓存
    public void doFindOneCache(UserReport userReport){
        logger.debug("设置userReport.findOne缓存：{}",userReport);
        redisService.set(RedisConstant.USER_REPORT_BY_ID_+userReport.getId(),userReport);
    }

    //总删除，包括从findAll和findOne中删除
    public void doDelCach(Long id){
        doDelFindAllCache(id);
        doDelFindOneCache(id);
    }

    //从findOne缓存中删除
    public void doDelFindOneCache(Long id){
        logger.debug("从userReport.findOne缓存中删掉id为{}的记录");
        redisService.del(RedisConstant.USER_REPORT_BY_ID_+id);
    }

    //从findAll缓存列表中删除元素
    public void doDelFindAllCache(Long id){
        List<UserReport> userReports = redisService.get(RedisConstant.USER_REPORT_LIST);
        if(userReports!=null) {
            Iterator<UserReport> userReportIterator = userReports.iterator();
            while (userReportIterator.hasNext()) {
                UserReport userReportEntity = userReportIterator.next();
                if (userReportEntity.getId().equals(id)) {
                    userReportIterator.remove();
                    redisService.set(RedisConstant.USER_REPORT_LIST, userReports);
                    logger.debug("从 userReport.findAll缓存中删掉id为{}的元素", id);
                    return;
                }
            }
        }
    }

    //更新findAll中的缓存
    public void doSaveFindAllCache(UserReport userReport){
        List<UserReport> userReports = findAllByCache();
        if(userReports!=null){
            //当该list中已经存在该对象时替换该对象
            for(int i = 0;i<userReports.size();i++){
                UserReport userReportCache = userReports.get(i);
                if(userReportCache.getId().equals(userReport.getId())){
                    logger.debug("在userReport.findAll缓存中替换id为{}的值",userReport.getId());
                    userReports.set(i,userReport);
                    return;
                }
            }
            //如果在列表缓存中不存在该值，就添加该元素
            userReports.add(userReport);
            redisService.set(RedisConstant.USER_REPORT_LIST,userReports);
        }
    }

    //设置cache list缓存
    public void doSetFindAllCache(List<UserReport> userReports){
        int size = 0;
        if(userReports!=null){
            size = userReports.size();
        }
        logger.debug("设置userReport.findAll缓存：size:{},{}",size,userReports);
        redisService.set(RedisConstant.USER_REPORT_LIST,userReports);
    }

    //根据id从findOne缓存中获取对象
    public UserReport findOneByCache(Long id){
        UserReport userReport = redisService.get(RedisConstant.USER_REPORT_BY_ID_+id);
        logger.debug("从redis缓存中获取userReport.findOne缓存，id为{}，value为{}",id,userReport);
        return userReport;
    }

    //从缓存中获取findAll缓存列表
    public List<UserReport> findAllByCache(){

        List<UserReport> userReports = redisService.get(RedisConstant.USER_REPORT_LIST);
        int size = 0;
        if(userReports!=null){
            size = userReports.size();
        }
        logger.debug("从redis缓存中获取userReport.findAll缓存，size为{}，value为{}",size,userReports);
        return userReports;
    }
}
