package com.fieldops.mstiposervicio.controller;

import com.fieldops.mstiposervicio.dto.ApiResponse;
import com.fieldops.mstiposervicio.dto.TipoServicioRequest;
import com.fieldops.mstiposervicio.dto.TipoServicioResponse;
import com.fieldops.mstiposervicio.service.TipoServicioService;
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
@RequestMapping("/api/tipos-servicio")
@RequiredArgsConstructor
public class TipoServicioController {

    private final TipoServicioService service;

    @PostMapping
    public ResponseEntity<ApiResponse<TipoServicioResponse>> create(@Valid @RequestBody TipoServicioRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<TipoServicioResponse>builder().message("Recurso creado correctamente").data(service.create(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TipoServicioResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.<List<TipoServicioResponse>>builder().message("Listado obtenido").data(service.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TipoServicioResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<TipoServicioResponse>builder().message("Recurso encontrado").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TipoServicioResponse>> update(@PathVariable Long id, @Valid @RequestBody TipoServicioRequest request) {
        return ResponseEntity.ok(ApiResponse.<TipoServicioResponse>builder().message("Recurso actualizado").data(service.update(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().message("Recurso eliminado").data(null).build());
    }
}