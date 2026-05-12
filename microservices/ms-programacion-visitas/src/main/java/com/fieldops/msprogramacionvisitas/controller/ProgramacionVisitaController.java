package com.fieldops.msprogramacionvisitas.controller;

import com.fieldops.msprogramacionvisitas.dto.ApiResponse;
import com.fieldops.msprogramacionvisitas.dto.ProgramacionVisitaRequest;
import com.fieldops.msprogramacionvisitas.dto.ProgramacionVisitaResponse;
import com.fieldops.msprogramacionvisitas.service.ProgramacionVisitaService;
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
@RequestMapping("/api/programaciones-visita")
@RequiredArgsConstructor
public class ProgramacionVisitaController {

    private final ProgramacionVisitaService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ProgramacionVisitaResponse>> create(@Valid @RequestBody ProgramacionVisitaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ProgramacionVisitaResponse>builder().message("Recurso creado correctamente").data(service.create(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProgramacionVisitaResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.<List<ProgramacionVisitaResponse>>builder().message("Listado obtenido").data(service.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProgramacionVisitaResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<ProgramacionVisitaResponse>builder().message("Recurso encontrado").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProgramacionVisitaResponse>> update(@PathVariable Long id, @Valid @RequestBody ProgramacionVisitaRequest request) {
        return ResponseEntity.ok(ApiResponse.<ProgramacionVisitaResponse>builder().message("Recurso actualizado").data(service.update(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().message("Recurso eliminado").data(null).build());
    }
}