package com.ifrabbit.nk.redisService;

import com.ifrabbit.nk.constant.RedisConstant;
import com.ifrabbit.nk.usercenter.domain.Company;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyRedisService {
    @Autowired
    private RedisService redisService;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(CompanyRedisService.class);
    /**
     * 当添加或者修改记录的时候执行该方法
     * @param company
     */
    public void doCache(Company company){
        doFindOneCache(company);
    }

    //是否初始化
    public boolean isInit(){
        Boolean isInit = redisService.get(RedisConstant.COMPANY_IS_INIT);
        redisService.set(RedisConstant.COMPANY_IS_INIT,true);

        if(isInit==null){
            return false;
        }else{
            return isInit;
        }

    }

    //更新根据网点id从缓存中获取公司对象的缓存
    public void doFindOneCache(Company company){
        /**
         * 更新获取网点缓存
         */
        redisService.set(RedisConstant.COMPANY_BY_ID_+company.getId(),company);
        logger.debug("更新id为："+company.getId()+"的网点到缓存");
    }

    public void doDelFindOneCache(Long id){
        redisService.del(RedisConstant.COMPANY_BY_ID_+id);
        logger.debug("删除id为："+id+"的网点缓存");
    }

    //根据id从缓存中获取网点对象
    public Company findOneByCache(Long id){
        Company company = redisService.get(RedisConstant.COMPANY_BY_ID_+id);
        return company;
    }
}
