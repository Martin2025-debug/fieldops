package com.fieldops.msinformes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InformeServicioApplication {

    public static void main(String[] args) {
        SpringApplication.run(InformeServicioApplication.class, args);
    }
}