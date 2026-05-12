package com.fieldops.mstiposervicio.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TipoServicioDetalleResponse {
    private Long id;
    private String observacion;
    private LocalDateTime fechaRegistro;
}