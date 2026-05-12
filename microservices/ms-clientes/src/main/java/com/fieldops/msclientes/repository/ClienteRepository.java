package com.fieldops.msclientes.repository;

import com.fieldops.msclientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}