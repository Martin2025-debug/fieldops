package com.fieldops.msevidencias.repository;

import com.fieldops.msevidencias.model.Evidencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvidenciaRepository extends JpaRepository<Evidencia, Long> {
}