package com.fieldops.msevidencias;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EvidenciaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EvidenciaApplication.class, args);
    }
}