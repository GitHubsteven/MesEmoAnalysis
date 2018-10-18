package com.mea.web.cache;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;

/**
 * @version 1.0.0 COPYRIGHT © 2001 - 2018 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 * @Author jet.xie
 * @Description:
 * @Date: Created at 12:44 2018/10/17.
 */
public class RedisCacheConfigurationSupport extends CachingConfigurerSupport {
    @Override
    public KeyGenerator keyGenerator() {
        return super.keyGenerator();
    }
}