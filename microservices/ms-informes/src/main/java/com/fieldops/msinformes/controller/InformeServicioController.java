package com.fieldops.msinformes.controller;

import com.fieldops.msinformes.dto.ApiResponse;
import com.fieldops.msinformes.dto.InformeServicioRequest;
import com.fieldops.msinformes.dto.InformeServicioResponse;
import com.fieldops.msinformes.service.InformeServicioService;
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
@RequestMapping("/api/informes")
@RequiredArgsConstructor
public class InformeServicioController {

    private final InformeServicioService service;

    @PostMapping
    public ResponseEntity<ApiResponse<InformeServicioResponse>> create(@Valid @RequestBody InformeServicioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<InformeServicioResponse>builder().message("Recurso creado correctamente").data(service.create(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<InformeServicioResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.<List<InformeServicioResponse>>builder().message("Listado obtenido").data(service.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InformeServicioResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<InformeServicioResponse>builder().message("Recurso encontrado").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<InformeServicioResponse>> update(@PathVariable Long id, @Valid @RequestBody InformeServicioRequest request) {
        return ResponseEntity.ok(ApiResponse.<InformeServicioResponse>builder().message("Recurso actualizado").data(service.update(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().message("Recurso eliminado").data(null).build());
    }
}