package com.sportsmeeting;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sportsmeeting.mapper")
public class SportsMeetingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SportsMeetingApplication.class, args);
    }
}
