package com.gov.purchase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import zipkin.server.EnableZipkinServer;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@EnableZipkinServer
public class SleuthApplication {

    public static void main(String[] args){
        SpringApplication.run(SleuthApplication.class,args);
    }
}
