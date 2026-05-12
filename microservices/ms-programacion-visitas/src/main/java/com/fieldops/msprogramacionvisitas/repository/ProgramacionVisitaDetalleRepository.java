package com.fieldops.msprogramacionvisitas.repository;

import com.fieldops.msprogramacionvisitas.model.ProgramacionVisitaDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramacionVisitaDetalleRepository extends JpaRepository<ProgramacionVisitaDetalle, Long> {
    List<ProgramacionVisitaDetalle> findByPadreId(Long padreId);
}