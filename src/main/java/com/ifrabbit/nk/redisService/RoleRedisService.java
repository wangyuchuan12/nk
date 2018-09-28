package com.ifrabbit.nk.redisService;

import com.ifrabbit.nk.constant.RedisConstant;
import com.ifrabbit.nk.usercenter.domain.Role;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.List;


@Service
public class RoleRedisService {
    @Autowired
    private RedisService redisService;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RoleRedisService.class);

    //新增缓存
    public void doCache(Role role){ this.doFindOneCache(role); }


    //缓存初始化
    public boolean isInit(){
        Boolean isInit = redisService.get(RedisConstant.ROLE_IS_INIT);
        redisService.set(RedisConstant.ROLE_IS_INIT,true);
        if(isInit==null){
            return false;
        }else{
            return isInit;
        }
    }


    //新增缓存,根据角色id
    public void doFindOneCache(Role role){
        logger.info("===========新增的缓存为============="+role);
        redisService.set(RedisConstant.ROLE_BY_ID_+role.getId(),role);
    }

    //根据id删除缓存
    public void doDelCache(String id){
        doDelFindAllCache(id);
        doDelFindOneCache(id);
    }

    //根据id删除缓存
    public void doDelFindOneCache(String id){
        logger.info("===========删除的缓存id为============="+id);
        redisService.del(RedisConstant.ROLE_BY_ID_+id);
    }

    //从findAll缓存列表中删除元素
    public void doDelFindAllCache(String id){
        List<Role> roles = redisService.get(RedisConstant.ROLE_LIST);
        if(roles!=null) {
            Iterator<Role> roleIterator = roles.iterator();
            while (roleIterator.hasNext()) {
                Role roleEntity = roleIterator.next();
                if (roleEntity.getId().equals(id)) {
                    roleIterator.remove();
                    redisService.set(RedisConstant.ROLE_LIST, roles);
                    logger.info("===========缓存列表删除的id为============="+id);
                    return;
                }
            }
        }
    }
}
