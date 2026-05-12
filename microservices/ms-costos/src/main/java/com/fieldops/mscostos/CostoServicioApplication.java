package com.fieldops.mscostos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CostoServicioApplication {

    public static void main(String[] args) {
        SpringApplication.run(CostoServicioApplication.class, args);
    }
}