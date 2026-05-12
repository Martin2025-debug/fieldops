package com.fieldops.msevidencias.controller;

import com.fieldops.msevidencias.dto.ApiResponse;
import com.fieldops.msevidencias.dto.EvidenciaRequest;
import com.fieldops.msevidencias.dto.EvidenciaResponse;
import com.fieldops.msevidencias.service.EvidenciaService;
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
@RequestMapping("/api/evidencias")
@RequiredArgsConstructor
public class EvidenciaController {

    private final EvidenciaService service;

    @PostMapping
    public ResponseEntity<ApiResponse<EvidenciaResponse>> create(@Valid @RequestBody EvidenciaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<EvidenciaResponse>builder().message("Recurso creado correctamente").data(service.create(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EvidenciaResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.<List<EvidenciaResponse>>builder().message("Listado obtenido").data(service.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EvidenciaResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<EvidenciaResponse>builder().message("Recurso encontrado").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EvidenciaResponse>> update(@PathVariable Long id, @Valid @RequestBody EvidenciaRequest request) {
        return ResponseEntity.ok(ApiResponse.<EvidenciaResponse>builder().message("Recurso actualizado").data(service.update(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().message("Recurso eliminado").data(null).build());
    }
}