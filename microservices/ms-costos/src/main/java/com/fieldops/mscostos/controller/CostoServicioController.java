package com.fieldops.mscostos.controller;

import com.fieldops.mscostos.dto.ApiResponse;
import com.fieldops.mscostos.dto.CostoServicioRequest;
import com.fieldops.mscostos.dto.CostoServicioResponse;
import com.fieldops.mscostos.service.CostoServicioService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/costos")
@RequiredArgsConstructor
public class CostoServicioController {

    private final CostoServicioService service;

    @PostMapping
    public ResponseEntity<ApiResponse<CostoServicioResponse>> create(@Valid @RequestBody CostoServicioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CostoServicioResponse>builder().message("Recurso creado correctamente").data(service.create(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CostoServicioResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.<List<CostoServicioResponse>>builder().message("Listado obtenido").data(service.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CostoServicioResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<CostoServicioResponse>builder().message("Recurso encontrado").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CostoServicioResponse>> update(@PathVariable Long id, @Valid @RequestBody CostoServicioRequest request) {
        return ResponseEntity.ok(ApiResponse.<CostoServicioResponse>builder().message("Recurso actualizado").data(service.update(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().message("Recurso eliminado").data(null).build());
    }
}