package com.mea.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @version 1.0.0 COPYRIGHT © 2001 - 2018 VOYAGE ONE GROUP INC. ALL RIGHTS RESERVED.
 * @Author jet.xie
 * @Description:
 * @Date: Created at 14:44 2018/10/12.
 */
@EnableAutoConfiguration     //enable this custom application
@SpringBootConfiguration
@ComponentScan(basePackages = {"com.mea"}, lazyInit = true)     //default load mode is lazyInit
public class MeaWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeaWebApplication.class, args);
    }
}