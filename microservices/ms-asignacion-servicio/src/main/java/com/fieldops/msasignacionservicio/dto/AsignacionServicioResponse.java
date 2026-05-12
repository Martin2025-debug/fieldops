package com.fieldops.msasignacionservicio.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AsignacionServicioResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    private Long referenciaExternaId;
    private LocalDateTime fechaCreacion;
    private List<AsignacionServicioDetalleResponse> detalles;
}