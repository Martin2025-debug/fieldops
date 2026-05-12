package com.fieldops.mstiposervicio.repository;

import com.fieldops.mstiposervicio.model.TipoServicioDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoServicioDetalleRepository extends JpaRepository<TipoServicioDetalle, Long> {
    List<TipoServicioDetalle> findByPadreId(Long padreId);
}