package com.fieldops.msclientes.controller;

import com.fieldops.msclientes.dto.ApiResponse;
import com.fieldops.msclientes.dto.ClienteRequest;
import com.fieldops.msclientes.dto.ClienteResponse;
import com.fieldops.msclientes.service.ClienteService;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<ApiResponse<ClienteResponse>> create(@Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.<ClienteResponse>builder().message("Recurso creado correctamente").data(service.create(request)).build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ClienteResponse>>> findAll() {
        return ResponseEntity.ok(ApiResponse.<List<ClienteResponse>>builder().message("Listado obtenido").data(service.findAll()).build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<ClienteResponse>builder().message("Recurso encontrado").data(service.findById(id)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ClienteResponse>> update(@PathVariable Long id, @Valid @RequestBody ClienteRequest request) {
        return ResponseEntity.ok(ApiResponse.<ClienteResponse>builder().message("Recurso actualizado").data(service.update(id, request)).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder().message("Recurso eliminado").data(null).build());
    }
}