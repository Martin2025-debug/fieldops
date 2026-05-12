package com.fieldops.msasignacionservicio.feign;

import com.fieldops.msasignacionservicio.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tecnicosclient", url = "http://localhost:8082", configuration = FeignClientConfig.class)
public interface TecnicosClient {

    @GetMapping("/api/tecnicos/{id}")
    Object getTecnicoById(@PathVariable("id") Long id);
}