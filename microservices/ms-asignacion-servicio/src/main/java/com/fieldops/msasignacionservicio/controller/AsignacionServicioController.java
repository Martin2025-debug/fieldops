package com.fieldops.msasignacionservicio.controller;

import com.fieldops.msasignacionservicio.dto.ApiResponse;
import com.fieldops.msasignacionservicio.dto.AsignacionServicioRequest;
import com.fieldops.msasignacionservicio.dto.AsignacionServicioResponse;
import com.fieldops.msasignacionservicio.service.AsignacionServicioService;
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
@RequestMapping("/api/asignaciones-servicio")
@RequiredArgsConstructor
public class AsignacionServicioController {

    private final AsignacionServicioService service;

    @PostMapping
    public ResponseEntity<ApiResponse<AsignacionServicioResponse>> create(@Valid @RequestBody AsignacionServicioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<AsignacionServicioResponse>builder().message("Recurso creado correctamente").data(service.create(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AsignacionServicioResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.<List<AsignacionServicioResponse>>builder().message("Listado obtenido").data(service.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AsignacionServicioResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<AsignacionServicioResponse>builder().message("Recurso encontrado").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AsignacionServicioResponse>> update(@PathVariable Long id, @Valid @RequestBody AsignacionServicioRequest request) {
        return ResponseEntity.ok(ApiResponse.<AsignacionServicioResponse>builder().message("Recurso actualizado").data(service.update(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().message("Recurso eliminado").data(null).build());
    }
}