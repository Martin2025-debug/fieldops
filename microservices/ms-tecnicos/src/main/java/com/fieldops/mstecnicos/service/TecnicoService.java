package com.fieldops.mstecnicos.service;

import com.fieldops.mstecnicos.dto.TecnicoDetalleResponse;
import com.fieldops.mstecnicos.dto.TecnicoRequest;
import com.fieldops.mstecnicos.dto.TecnicoResponse;
import com.fieldops.mstecnicos.exception.IntegrationException;
import com.fieldops.mstecnicos.exception.ResourceNotFoundException;
import com.fieldops.mstecnicos.model.TecnicoDetalle;
import com.fieldops.mstecnicos.model.Tecnico;
import com.fieldops.mstecnicos.repository.TecnicoRepository;
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
public class TecnicoService {

    private final TecnicoRepository repository;

    @Transactional
    public TecnicoResponse create(TecnicoRequest request) {
        log.info("Creando recurso en ms-tecnicos nombre={} estado={}", request.getNombre(), request.getEstado());
        try {

            Tecnico entity = Tecnico.builder()
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .estado(request.getEstado())
                    .referenciaExternaId(request.getReferenciaExternaId())
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            List<String> obs = request.getObservaciones() == null ? Collections.emptyList() : request.getObservaciones();
            obs.forEach(item -> entity.addDetalle(new TecnicoDetalle(null, item, LocalDateTime.now(), null)));

            Tecnico saved = repository.save(entity);
            log.info("Recurso creado correctamente id={}", saved.getId());
            return toResponse(saved);
        } catch (IntegrationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error creando recurso en ms-tecnicos", ex);
            throw ex;
        }
    }

    public List<TecnicoResponse> findAll() {
        log.info("Listando recursos de ms-tecnicos");
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public TecnicoResponse findById(Long id) {
        log.info("Buscando recurso id={} en ms-tecnicos", id);
        return toResponse(getById(id));
    }

    @Transactional
    public TecnicoResponse update(Long id, TecnicoRequest request) {
        log.info("Actualizando recurso id={} en ms-tecnicos", id);
        Tecnico entity = getById(id);
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setEstado(request.getEstado());
        entity.setReferenciaExternaId(request.getReferenciaExternaId());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando recurso id={} en ms-tecnicos", id);
        repository.delete(getById(id));
    }

    private Tecnico getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));
    }

    private TecnicoResponse toResponse(Tecnico entity) {
        return TecnicoResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .referenciaExternaId(entity.getReferenciaExternaId())
                .fechaCreacion(entity.getFechaCreacion())
                .detalles(entity.getDetalles().stream().map(d -> TecnicoDetalleResponse.builder()
                        .id(d.getId())
                        .observacion(d.getObservacion())
                        .fechaRegistro(d.getFechaRegistro())
                        .build()).toList())
                .build();
    }
}