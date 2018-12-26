package com.yxbkj.yxb.common.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Description ： redis 工具类
 * Author： 李明
 * Date： 2018/07/30 11:10
 */
@Component
public class RedisTemplateUtils {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 缓存value操作
     *
     * @param key
     * @param value
     * @param expireTime 秒, 小于0，则不设置过期时间
     * @return boolean
     */
    public boolean stringAdd(String key, String value, int expireTime) {
        try {
            ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
            if (expireTime > 0) {
                valueOps.set(key, value, expireTime, TimeUnit.SECONDS);
            } else {
                valueOps.set(key, value);
            }
            return true;
        } catch (Throwable t) {
            logger.error("缓存key={}失败, value={}, 异常{}", key, value, t);
        }
        return false;
    }

    /**
     * 获取缓存
     *
     * @param key key
     * @return string
     */
    public String getStringValue(String key) {
        try {
            ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
            return valueOps.get(key);
        } catch (Throwable t) {
            logger.error("获取缓存失败key={}, 异常{}", key, t);
        }
        return null;
    }

    /**
     * stringRedisTemplate 设置过期时间
     *
     * @param key
     * @param time
     * @param unit
     * @return
     */
    public boolean expire(String key, long time, TimeUnit unit) {
        try {
            return stringRedisTemplate.expire(key, time, unit);//设置过期时间
        } catch (Throwable t) {
            logger.error("设置缓存过期时间失败key={}, 异常{}", key, t);
        }
        return false;
    }

    /**
     * stringRedisTemplate 设置缓存过期时间，以秒为单位
     *
     * @param key
     * @param time
     * @return
     */
    public boolean expireSeconds(String key, long time) {
        return this.expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 判断key 是否存在
     *
     * @param key
     * @return
     */
    public boolean containsKey(String key) {
        try {
            return stringRedisTemplate.hasKey(key);
        } catch (Throwable t) {
            logger.error("判断缓存存在失败key={}, 异常{}", key, t);
        }
        return false;
    }

    /**
     * 删除缓存key
     *
     * @param key key
     * @return boolean
     */
    public boolean detele(String key) {
        try {
            stringRedisTemplate.delete(key);
            return true;
        } catch (Throwable t) {
            logger.error("删除缓存失败key={}, 异常 {}", key, t);
        }
        return false;
    }

    /**
     * set集合添加
     *
     * @param key
     * @param value
     * @param expireTime 过期时间，单位为秒
     * @return
     */
    public boolean sadd(String key, String value, int expireTime) {
        try {
            SetOperations<String, String> setOperations = stringRedisTemplate.opsForSet();
            if (setOperations.add(key, value) == 1) {
                if (expireTime > 0) {
                    this.expireSeconds(key, expireTime);
                }
                return true;
            } else {
                return false;
            }
        } catch (Throwable t) {
            logger.error("set集合添加缓存失败key={},value={}, 异常 {}", key, value, t);
        }
        return false;
    }

    /**
     * 获取key值所对应set的元素的个数
     * @param key   redis key
     * @return  set的元素的个数
     */
    public long ssize(String key){
        return stringRedisTemplate.opsForSet().size(key);
    }

    /**
     * 删除key所对应的set中的value
     * @param key   redis的key
     * @param value set中的值
     * @return  是否删除成功
     */
    public boolean sremove(String key,String value){
        if(stringRedisTemplate.opsForSet().remove(key,value) == 1){
            return true;
        }
        return false;
    }
    /**
     * hash添加
     *
     * @param key
     * @param value
     * @param expireTime 过期时间，单位为秒
     * @return
     */
    public boolean hashAdd(String key, String hkey, String value, int expireTime) {
        try {
            HashOperations<String, String, String> setOperations = stringRedisTemplate.opsForHash();
            setOperations.put(key, hkey, value);
            if (expireTime > 0) {
                this.expireSeconds(key, expireTime);
            }
            return true;
        } catch (Throwable t) {
            logger.error("hashAdd添加缓存失败key={},hkey={},value={}, 异常 {}", key, hkey, value, t);
        }
        return false;
    }

    /**
     * hash 添加多个
     * @param key
     * @param map
     * @param expireTime
     * @return
     */
    public boolean hashAddAll(String key, Map<String, String> map, int expireTime) {
        try{
            HashOperations<String, String, Object> setOperations = stringRedisTemplate.opsForHash();
            setOperations.putAll(key, map);
            if (expireTime > 0) {
                this.expireSeconds(key, expireTime);
            }
            return true;
        } catch (Throwable t) {
            logger.error("haddAll添加缓存失败key={},map={}, 异常 {}", key, map, t);
        }

        return false;
    }

    /**
     * redisTemplate 设置过期时间
     *
     * @param key
     * @param time
     * @param unit
     * @return
     */
    public boolean expireT(String key, long time, TimeUnit unit) {
        try {
            return redisTemplate.expire(key, time, unit);//设置过期时间
        } catch (Throwable t) {
            logger.error("设置缓存过期时间失败key={}, 异常{}", key, t);
        }
        return false;
    }

    /**
     * 获取hashvalue
     *
     * @param key
     * @return
     */
    public String getHashValue(String key, String hkey) {
        try {

            HashOperations<String, String, String> setOperations = stringRedisTemplate.opsForHash();
            return setOperations.get(key, hkey);
        } catch (Throwable t) {
            logger.error("getHashValue获取缓存失败key={}, hkey={}, 异常 {}", key, hkey, t);
        }
        return null;
    }


    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 自增一个数量
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public int incre(String key, Object value, double delta, int time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
        return redisTemplate.opsForZSet().incrementScore(key, value, delta).intValue();
    }

    /**
     * 自减一个数量
     *
     * @param key
     * @param value
     * @param time
     * @return
     */
    public int sub(String key, Long value, int time) {
        if (time > 0) {
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
        }
        return redisTemplate.opsForValue().increment(key, value).intValue();
    }

    /**
     * 从左边放入队列
     *
     * @param key
     * @param value
     * @return
     */
    public Long leftPush(String key, String value) {
        try {
            return stringRedisTemplate.opsForList().leftPush(key, value);
        } catch (Throwable t) {
            logger.error("leftPush缓存失败key={}, value={}, 异常 {}", key, value, t);
        }
        return null;

    }

    /**
     * 从右边放入队列
     *
     * @param key
     * @param value
     * @return
     */
    public Long rightPush(String key, String value) {
        try {
            return stringRedisTemplate.opsForList().rightPush(key, value);
        } catch (Throwable t) {
            logger.error("rightPush缓存失败key={}, value={}, 异常 {}", key, value, t);
        }
        return null;

    }

    /**
     * 从右边 获取一个队列值
     *
     * @param key
     * @return
     */
    public String rightPop(String key) {
        try {
            return stringRedisTemplate.opsForList().rightPop(key);
        } catch (Throwable t) {
            logger.error("rightPop获取缓存失败key={}, 异常 {}", key, t);
        }
        return null;
    }


    /**
     * hash添加
     *
     * @param key
     * @param hashKey
     * @param value
     * @param expireTime 过期时间，单位为秒
     * @return
     */
    public boolean stringHashAdd(String key, String hashKey, String value, int expireTime) {
        try {

            HashOperations<String, String, String> setOperations = stringRedisTemplate.opsForHash();
            setOperations.put(key, hashKey, value);
            if (expireTime > 0) {
                this.expire(key, expireTime, TimeUnit.SECONDS);
            }
            return true;
        } catch (Throwable t) {
            logger.error("stringHashAdd添加缓存失败key={},hashKey={},value={}, 异常 {}", key, hashKey,value, t);
        }
        return false;
    }

}
