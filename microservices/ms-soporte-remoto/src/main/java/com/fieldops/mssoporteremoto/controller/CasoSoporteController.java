package com.fieldops.mssoporteremoto.controller;

import com.fieldops.mssoporteremoto.dto.ApiResponse;
import com.fieldops.mssoporteremoto.dto.CasoSoporteRequest;
import com.fieldops.mssoporteremoto.dto.CasoSoporteResponse;
import com.fieldops.mssoporteremoto.service.CasoSoporteService;
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
@RequestMapping("/api/soportes-remoto")
@RequiredArgsConstructor
public class CasoSoporteController {

    private final CasoSoporteService service;

    @PostMapping
    public ResponseEntity<ApiResponse<CasoSoporteResponse>> create(@Valid @RequestBody CasoSoporteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<CasoSoporteResponse>builder().message("Recurso creado correctamente").data(service.create(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CasoSoporteResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.<List<CasoSoporteResponse>>builder().message("Listado obtenido").data(service.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CasoSoporteResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<CasoSoporteResponse>builder().message("Recurso encontrado").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CasoSoporteResponse>> update(@PathVariable Long id, @Valid @RequestBody CasoSoporteRequest request) {
        return ResponseEntity.ok(ApiResponse.<CasoSoporteResponse>builder().message("Recurso actualizado").data(service.update(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().message("Recurso eliminado").data(null).build());
    }
}