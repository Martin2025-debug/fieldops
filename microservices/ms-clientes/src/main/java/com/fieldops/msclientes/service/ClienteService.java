package com.fieldops.msclientes.service;

import com.fieldops.msclientes.dto.ClienteDetalleResponse;
import com.fieldops.msclientes.dto.ClienteRequest;
import com.fieldops.msclientes.dto.ClienteResponse;
import com.fieldops.msclientes.exception.IntegrationException;
import com.fieldops.msclientes.exception.ResourceNotFoundException;
import com.fieldops.msclientes.model.ClienteDetalle;
import com.fieldops.msclientes.model.Cliente;
import com.fieldops.msclientes.repository.ClienteRepository;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository repository;

    @Transactional
    public ClienteResponse create(ClienteRequest request) {
        log.info("Creando recurso en ms-clientes nombre={} estado={}", request.getNombre(), request.getEstado());
        try {

            Cliente entity = Cliente.builder()
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .estado(request.getEstado())
                    .referenciaExternaId(request.getReferenciaExternaId())
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            List<String> obs = request.getObservaciones() == null ? Collections.emptyList() : request.getObservaciones();
            obs.forEach(item -> entity.addDetalle(new ClienteDetalle(null, item, LocalDateTime.now(), null)));

            Cliente saved = repository.save(entity);
            log.info("Recurso creado correctamente id={}", saved.getId());
            return toResponse(saved);
        } catch (IntegrationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error creando recurso en ms-clientes", ex);
            throw ex;
        }
    }

    public List<ClienteResponse> findAll() {
        log.info("Listando recursos de ms-clientes");
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ClienteResponse findById(Long id) {
        log.info("Buscando recurso id={} en ms-clientes", id);
        return toResponse(getById(id));
    }

    @Transactional
    public ClienteResponse update(Long id, ClienteRequest request) {
        log.info("Actualizando recurso id={} en ms-clientes", id);
        Cliente entity = getById(id);
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setEstado(request.getEstado());
        entity.setReferenciaExternaId(request.getReferenciaExternaId());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando recurso id={} en ms-clientes", id);
        repository.delete(getById(id));
    }

    private Cliente getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));
    }

    private ClienteResponse toResponse(Cliente entity) {
        return ClienteResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .referenciaExternaId(entity.getReferenciaExternaId())
                .fechaCreacion(entity.getFechaCreacion())
                .detalles(entity.getDetalles().stream().map(d -> ClienteDetalleResponse.builder()
                        .id(d.getId())
                        .observacion(d.getObservacion())
                        .fechaRegistro(d.getFechaRegistro())
                        .build()).toList())
                .build();
    }
}