package com.fieldops.mssoporteremoto.repository;

import com.fieldops.mssoporteremoto.model.CasoSoporteDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasoSoporteDetalleRepository extends JpaRepository<CasoSoporteDetalle, Long> {
    List<CasoSoporteDetalle> findByPadreId(Long padreId);
}