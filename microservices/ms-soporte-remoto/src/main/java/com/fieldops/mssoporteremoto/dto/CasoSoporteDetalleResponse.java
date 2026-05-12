package com.fieldops.mssoporteremoto.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CasoSoporteDetalleResponse {
    private Long id;
    private String observacion;
    private LocalDateTime fechaRegistro;
}