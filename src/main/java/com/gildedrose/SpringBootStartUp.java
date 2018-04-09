package com.gildedrose;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by andriusbaltrunas on 4/9/2018.
 */
@EnableAutoConfiguration
@SpringBootConfiguration
@ComponentScan("com.gildedrose")
@PropertySource(value = "classpath:application.properties")
public class SpringBootStartUp {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootStartUp.class, args);
    }
}
