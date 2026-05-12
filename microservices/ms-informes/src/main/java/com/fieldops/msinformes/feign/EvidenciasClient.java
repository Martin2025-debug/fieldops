package com.fieldops.msinformes.feign;

import com.fieldops.msinformes.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "evidenciasclient", url = "http://localhost:8088", configuration = FeignClientConfig.class)
public interface EvidenciasClient {

    @GetMapping("/api/evidencias/{id}")
    Object getEvidenciaById(@PathVariable("id") Long id);
}