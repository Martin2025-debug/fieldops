package com.fieldops.msinformes.repository;

import com.fieldops.msinformes.model.InformeServicioDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InformeServicioDetalleRepository extends JpaRepository<InformeServicioDetalle, Long> {
    List<InformeServicioDetalle> findByPadreId(Long padreId);
}