package com.fieldops.mssolicitudes.feign;

import com.fieldops.mssolicitudes.config.FeignClientConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "tiposervicioclient", url = "http://localhost:8085", configuration = FeignClientConfig.class)
public interface TipoServicioClient {

    @GetMapping("/api/tipos-servicio/{id}")
    Object getTipoServicioById(@PathVariable("id") Long id);
}