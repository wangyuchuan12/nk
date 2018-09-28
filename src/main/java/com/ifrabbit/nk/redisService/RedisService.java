package com.ifrabbit.nk.redisService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存service
 */
@Service
public class RedisService {
    @Autowired
    protected RedisTemplate redisTemplate;

    private static org.slf4j.Logger log = LoggerFactory.getLogger(RedisService.class);

    @PostConstruct
    public void init(){
        //设置序列化Key的实例化对象
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置序列化Value的实例化对象
        //redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        System.out.println(".....................测试jenkins......................");
    }

    /**
     * 写入缓存
     *
     * @param key 写入的key
     * @param value 写入的值
     * @param time 到期时间 t单位是秒
     */
    public  void set(final String key, final Object value, final long time) {
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }


    /**
     * 写入缓存
     * @param key
     * @param value
     */
    public  void set(final String key, final Object value) {
        redisTemplate.opsForValue().set(key, value);
    }
    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public  <T> T get(final String key) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    public  Object getObj(final String key){
        return redisTemplate.boundValueOps(key).get();
    }

    /**
     * 删除，根据key精确匹配
     *
     * @param key
     */
    public  void del(final String... key) {
        redisTemplate.delete(Arrays.asList(key));
    }

    /**
     * 批量删除，根据key模糊匹配
     *
     * @param pattern
     */
    public  void delpn(final String... pattern) {
        for (String kp : pattern) {
            redisTemplate.delete(redisTemplate.keys(kp + "*"));
        }
    }


    /**
     * 清空缓存
     * @return
     */
    public Object flushAll(){
        return redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    /**
     * key是否存在
     *
     * @param key
     */
    public  boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

}
