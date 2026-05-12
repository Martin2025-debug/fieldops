package com.fieldops.mssolicitudes.feign;

import com.fieldops.mssolicitudes.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientesclient", url = "http://localhost:8081", configuration = FeignClientConfig.class)
public interface ClientesClient {

    @GetMapping("/api/clientes/{id}")
    Object getClienteById(@PathVariable("id") Long id);
}