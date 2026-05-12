package com.fieldops.mssolicitudes.controller;

import com.fieldops.mssolicitudes.dto.ApiResponse;
import com.fieldops.mssolicitudes.dto.SolicitudRequest;
import com.fieldops.mssolicitudes.dto.SolicitudResponse;
import com.fieldops.mssolicitudes.service.SolicitudService;
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
@RequestMapping("/api/solicitudes")
@RequiredArgsConstructor
public class SolicitudController {

    private final SolicitudService service;

    @PostMapping
    public ResponseEntity<ApiResponse<SolicitudResponse>> create(@Valid @RequestBody SolicitudRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<SolicitudResponse>builder().message("Recurso creado correctamente").data(service.create(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SolicitudResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.<List<SolicitudResponse>>builder().message("Listado obtenido").data(service.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SolicitudResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<SolicitudResponse>builder().message("Recurso encontrado").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SolicitudResponse>> update(@PathVariable Long id, @Valid @RequestBody SolicitudRequest request) {
        return ResponseEntity.ok(ApiResponse.<SolicitudResponse>builder().message("Recurso actualizado").data(service.update(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().message("Recurso eliminado").data(null).build());
    }
}