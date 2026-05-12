package com.fieldops.msclientes.repository;

import com.fieldops.msclientes.model.ClienteDetalle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteDetalleRepository extends JpaRepository<ClienteDetalle, Long> {
    List<ClienteDetalle> findByPadreId(Long padreId);
}