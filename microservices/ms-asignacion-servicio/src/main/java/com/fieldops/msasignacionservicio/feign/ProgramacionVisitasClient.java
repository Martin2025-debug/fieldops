package com.fieldops.msasignacionservicio.feign;

import com.fieldops.msasignacionservicio.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "programacionvisitasclient", url = "http://localhost:8086", configuration = FeignClientConfig.class)
public interface ProgramacionVisitasClient {

    @GetMapping("/api/programaciones-visita/{id}")
    Object getProgramacionById(@PathVariable("id") Long id);
}