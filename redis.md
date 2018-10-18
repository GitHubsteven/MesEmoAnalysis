基于spring boot（前提条件：安装了redis）

项目依赖：
```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
```
基于spring-boot自带的redis jar包实现，添加配置条件，在application.properties中
```

#redis
spring.redis.host=127.0.0.1
#spring.redis.password= # redis password
spring.redis.port=6379

spring.redis.jedis.pool.max-idle=8
spring.redis.jedis.pool.min-idle=8
spring.redis.jedis.pool.max-active=8
spring.redis.jedis.pool.max-wait= 1ms
# 连接超时时间（毫秒）
spring.redis.timeout=6000ms

```

在项目中新建一个java class 类，基于spring中经典的factory/template模式来创建redis的template
```
public class RedisCache extends CachingConfigurerSupport {
    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式
     *
     * @param redisTemplate redis template
     * @param factory       redisConnectionFactory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, Object> redisTemplate, RedisConnectionFactory factory) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setConnectionFactory(factory);
    }

    /**
     * 实例化 HashOperations 对象,可以使用 Hash 类型操作
     *
     * @param redisTemplate redis template
     * @return instance to operate hashes
     */
    @Bean
    public HashOperations<String, String, Object> hashOperations(RedisTemplate<String, Object> redisTemplate) {
        return redisTemplate.opsForHash();
    }

   //注入各种操作类
}
```
生成RedisUtil.class
```
@Component
public class RedisUtil {
    @Resource
    private HashOperations<String, String, Object> hashOperations;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private ValueOperations<String, Object> valueOperations;

    @Resource
    private ListOperations<String, Object> listOperations;

    /**
     * find all key names matched regex
     *
     * @param key key with the regex *
     * @return key names
     */
    public Set<String> keys(String key) {
        return redisTemplate.keys(key);
    }

    /**
     * rename key 'key' after 'newKey'
     */
    public void renameKey(String key, String newKey) {
        redisTemplate.rename(key, newKey);
    }


    /**
     * 字符串添加信息
     *
     * @param key
     * @param obj 可以是单个的值，也可以是任意类型的对象
     */
    public void set(String key, Object obj) {
        valueOperations.set(key, obj);
    }
    
    //其他各种操作
```
把redis放在业务中调用，如下只是个case
```
package com.mea.web.controller;

import java.util.Map;
import java.util.Set;

/**
 * @version 1.0.0 COPYRIGHT © 2001 - 2018 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 * @Author jet.xie
 * @Description:
 * @Date: Created at 15:23 2018/10/12.
 */
@RestController
@RequestMapping(MesEmoAnaUrlConstant.User.ROOT)
public class UserController {
    private static final ObjectMapper OM = new ObjectMapper();

    @Autowired
    private RedisUtil redisUtil;

    //从redis中读取数据，不是真正的数据
    @RequestMapping(MesEmoAnaUrlConstant.User.CHECK)
    public JsonObj check(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        Object user = redisUtil.get("user:" + id);
        JsonObj resp = new JsonObj();
        resp.setCode("1");
        resp.setData(user);
        resp.setMessage("success");
        return resp;
    }
    
    //增加数据
    @RequestMapping(MesEmoAnaUrlConstant.User.ADD)
    public JsonObj add(@RequestBody Map<String, Object> params) {
        Integer id = (Integer) params.get("id");
        UserModel user = new UserModel();
        user.setAge((int) (Math.random() * 10));
        user.setMale("f");
        user.setName("userName");
        user.setPassword("1234");
        redisUtil.set("user:" + id, JacksonUtils.bean2Json(user));
        JsonObj resp = new JsonObj();
        resp.setCode("1");
        resp.setMessage("success");
        return resp;
    }
}
```
这里问题在于，我们没有结合数据库，这里只告诉我们怎么把redis应用在controller中。

