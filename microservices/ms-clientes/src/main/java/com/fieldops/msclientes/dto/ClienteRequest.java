package com.fieldops.msclientes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClienteRequest {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 120, message = "El nombre no puede superar 120 caracteres")
    private String nombre;

    @NotBlank(message = "La descripcion es obligatoria")
    @Size(max = 255, message = "La descripcion no puede superar 255 caracteres")
    private String descripcion;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 40, message = "El estado no puede superar 40 caracteres")
    private String estado;

    private Long referenciaExternaId;

    private List<@NotBlank(message = "La observacion no puede ir vacia") String> observaciones;
}