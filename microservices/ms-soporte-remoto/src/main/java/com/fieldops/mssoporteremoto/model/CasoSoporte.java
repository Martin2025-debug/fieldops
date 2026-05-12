package com.fieldops.mssoporteremoto.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "soportes_remoto")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CasoSoporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nombre;

    @Column(nullable = false, length = 255)
    private String descripcion;

    @Column(nullable = false, length = 40)
    private String estado;

    @Column(name = "referencia_externa_id")
    private Long referenciaExternaId;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @OneToMany(mappedBy = "padre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CasoSoporteDetalle> detalles = new ArrayList<>();

    public void addDetalle(CasoSoporteDetalle detalle) {
        detalle.setPadre(this);
        this.detalles.add(detalle);
    }
}