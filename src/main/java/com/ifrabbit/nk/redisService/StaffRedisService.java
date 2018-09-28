package com.ifrabbit.nk.redisService;

import com.ifrabbit.nk.constant.RedisConstant;
import com.ifrabbit.nk.usercenter.domain.Staff;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;


@Service
public class StaffRedisService {
    @Autowired
    private RedisService redisService;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(StaffRedisService.class);

    //增加findOne缓存和在findAll缓存中添加元素
    public void doCache(Staff staff){ this.doFindOneCache(staff); }


    public boolean isInit(){
        Boolean isInit = redisService.get(RedisConstant.STAFF_IS_INIT);
        redisService.set(RedisConstant.STAFF_IS_INIT,true);
        if(isInit==null){
            return false;
        }else{
            return isInit;
        }
    }


    //新增findOne缓存
    public void doFindOneCache(Staff staff){
        logger.info("===========新增的缓存为============="+staff);
        redisService.set(RedisConstant.STAFF_BY_ID_+staff.getId(),staff);
    }

    //总删除，包括从findAll和findOne中删除
    public void doDelCache(String id){
        doDelFindAllCache(id);
        doDelFindOneCache(id);
    }

    //从findOne缓存中删除
    public void doDelFindOneCache(String id){
        logger.info("===========删除的缓存id为============="+id);
        redisService.del(RedisConstant.STAFF_BY_ID_+id);
    }

    //从findAll缓存列表中删除元素
    public void doDelFindAllCache(String id){
        List<Staff> staffs = redisService.get(RedisConstant.STAFF_LIST);
        if(staffs!=null) {
            Iterator<Staff> staffIterator = staffs.iterator();
            while (staffIterator.hasNext()) {
                Staff staffEntity = staffIterator.next();
                if (staffEntity.getId().equals(id)) {
                    staffIterator.remove();
                    redisService.set(RedisConstant.STAFF_LIST, staffs);
                    logger.info("===========缓存列表删除的id为============="+id);
                    return;
                }
            }
        }
    }

}
