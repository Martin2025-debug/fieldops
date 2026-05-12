package com.fieldops.mstecnicos.controller;

import com.fieldops.mstecnicos.dto.ApiResponse;
import com.fieldops.mstecnicos.dto.TecnicoRequest;
import com.fieldops.mstecnicos.dto.TecnicoResponse;
import com.fieldops.mstecnicos.service.TecnicoService;
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
@RequestMapping("/api/tecnicos")
@RequiredArgsConstructor
public class TecnicoController {

    private final TecnicoService service;

    @PostMapping
    public ResponseEntity<ApiResponse<TecnicoResponse>> create(@Valid @RequestBody TecnicoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TecnicoResponse>builder().message("Recurso creado correctamente").data(service.create(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TecnicoResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.<List<TecnicoResponse>>builder().message("Listado obtenido").data(service.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TecnicoResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<TecnicoResponse>builder().message("Recurso encontrado").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TecnicoResponse>> update(@PathVariable Long id, @Valid @RequestBody TecnicoRequest request) {
        return ResponseEntity.ok(ApiResponse.<TecnicoResponse>builder().message("Recurso actualizado").data(service.update(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().message("Recurso eliminado").data(null).build());
    }
}