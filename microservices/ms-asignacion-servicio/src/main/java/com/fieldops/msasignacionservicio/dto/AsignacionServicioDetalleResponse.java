package com.fieldops.msasignacionservicio.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AsignacionServicioDetalleResponse {
    private Long id;
    private String observacion;
    private LocalDateTime fechaRegistro;
}