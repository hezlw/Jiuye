package com.bsjob;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.bsjob.mapper")
public class BsjobApplication {
    public static void main(String[] args) {
        SpringApplication.run(BsjobApplication.class, args);
    }
}
