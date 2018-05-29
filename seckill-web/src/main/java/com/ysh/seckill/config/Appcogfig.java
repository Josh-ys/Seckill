package com.ysh.seckill.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author joey
 * @date 2018/05/27 21:30
 */
@Getter
@Configuration
@PropertySource(value = "classpath:application.properties")
public class Appcogfig {

    @Autowired
    private Environment environment;

    public String getValue(String key) {
        return environment.getProperty(key);
    }

    public <T> T getValue(String key, Class<T> clazz) {
        return environment.getProperty(key, clazz);
    }
}
