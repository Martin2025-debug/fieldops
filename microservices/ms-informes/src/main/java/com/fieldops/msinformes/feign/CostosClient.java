package com.fieldops.msinformes.feign;

import com.fieldops.msinformes.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "costosclient", url = "http://localhost:8089", configuration = FeignClientConfig.class)
public interface CostosClient {

    @GetMapping("/api/costos/{id}")
    Object getCostoById(@PathVariable("id") Long id);
}