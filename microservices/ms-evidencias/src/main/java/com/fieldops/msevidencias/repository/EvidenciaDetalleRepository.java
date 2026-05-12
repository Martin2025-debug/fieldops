package com.fieldops.msevidencias.repository;

import com.fieldops.msevidencias.model.EvidenciaDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvidenciaDetalleRepository extends JpaRepository<EvidenciaDetalle, Long> {
    List<EvidenciaDetalle> findByPadreId(Long padreId);
}