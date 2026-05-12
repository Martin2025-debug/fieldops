package com.fieldops.mssolicitudes.repository;

import com.fieldops.mssolicitudes.model.SolicitudDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudDetalleRepository extends JpaRepository<SolicitudDetalle, Long> {
    List<SolicitudDetalle> findByPadreId(Long padreId);
}