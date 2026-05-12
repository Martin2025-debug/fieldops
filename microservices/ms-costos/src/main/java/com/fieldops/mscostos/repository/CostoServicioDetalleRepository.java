package com.fieldops.mscostos.repository;

import com.fieldops.mscostos.model.CostoServicioDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CostoServicioDetalleRepository extends JpaRepository<CostoServicioDetalle, Long> {
    List<CostoServicioDetalle> findByPadreId(Long padreId);
}