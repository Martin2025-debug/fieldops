package com.fieldops.msinformes.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InformeServicioDetalleResponse {
    private Long id;
    private String observacion;
    private LocalDateTime fechaRegistro;
}