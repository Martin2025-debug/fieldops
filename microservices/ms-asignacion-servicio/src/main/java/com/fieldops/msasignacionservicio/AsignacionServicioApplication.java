package com.fieldops.msasignacionservicio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AsignacionServicioApplication {

    public static void main(String[] args) {
        SpringApplication.run(AsignacionServicioApplication.class, args);
    }
}