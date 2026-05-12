package com.fieldops.mstecnicos.repository;

import com.fieldops.mstecnicos.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
}