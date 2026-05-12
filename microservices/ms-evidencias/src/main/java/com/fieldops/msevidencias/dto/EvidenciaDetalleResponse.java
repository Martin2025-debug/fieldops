package com.fieldops.msevidencias.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EvidenciaDetalleResponse {
    private Long id;
    private String observacion;
    private LocalDateTime fechaRegistro;
}