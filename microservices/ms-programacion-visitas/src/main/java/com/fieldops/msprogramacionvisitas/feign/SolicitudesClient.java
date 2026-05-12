package com.fieldops.msprogramacionvisitas.feign;

import com.fieldops.msprogramacionvisitas.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "solicitudesclient", url = "http://localhost:8083", configuration = FeignClientConfig.class)
public interface SolicitudesClient {

    @GetMapping("/api/solicitudes/{id}")
    Object getSolicitudById(@PathVariable("id") Long id);
}