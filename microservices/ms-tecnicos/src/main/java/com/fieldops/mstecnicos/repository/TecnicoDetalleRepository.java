package com.fieldops.mstecnicos.repository;

import com.fieldops.mstecnicos.model.TecnicoDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoDetalleRepository extends JpaRepository<TecnicoDetalle, Long> {
    List<TecnicoDetalle> findByPadreId(Long padreId);
}