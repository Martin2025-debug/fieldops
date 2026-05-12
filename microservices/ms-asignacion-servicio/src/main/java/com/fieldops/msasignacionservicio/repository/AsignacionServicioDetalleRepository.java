package com.fieldops.msasignacionservicio.repository;

import com.fieldops.msasignacionservicio.model.AsignacionServicioDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignacionServicioDetalleRepository extends JpaRepository<AsignacionServicioDetalle, Long> {
    List<AsignacionServicioDetalle> findByPadreId(Long padreId);
}