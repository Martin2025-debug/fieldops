package com.fieldops.mssoporteremoto.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CasoSoporteResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    private Long referenciaExternaId;
    private LocalDateTime fechaCreacion;
    private List<CasoSoporteDetalleResponse> detalles;
}