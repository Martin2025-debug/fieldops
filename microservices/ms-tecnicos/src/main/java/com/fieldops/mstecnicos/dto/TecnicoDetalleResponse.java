package com.fieldops.mstecnicos.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TecnicoDetalleResponse {
    private Long id;
    private String observacion;
    private LocalDateTime fechaRegistro;
}