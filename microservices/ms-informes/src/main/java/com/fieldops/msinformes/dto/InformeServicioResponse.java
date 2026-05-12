package com.fieldops.msinformes.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class InformeServicioResponse {
    private Long id;
    private String nombre;
    private String descripcion;
    private String estado;
    private Long referenciaExternaId;
    private LocalDateTime fechaCreacion;
    private List<InformeServicioDetalleResponse> detalles;
}