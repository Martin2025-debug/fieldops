package com.fieldops.mssoporteremoto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CasoSoporteApplication {

    public static void main(String[] args) {
        SpringApplication.run(CasoSoporteApplication.class, args);
    }
}