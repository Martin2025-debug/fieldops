package com.fieldops.mssolicitudes.repository;

import com.fieldops.mssolicitudes.model.Solicitud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
}