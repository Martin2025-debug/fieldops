package com.fieldops.msevidencias.feign;

import com.fieldops.msevidencias.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "asignacionesclient", url = "http://localhost:8087", configuration = FeignClientConfig.class)
public interface AsignacionesClient {

    @GetMapping("/api/asignaciones-servicio/{id}")
    Object getAsignacionById(@PathVariable("id") Long id);
}