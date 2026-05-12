package com.fieldops.msprogramacionvisitas.service;

import com.fieldops.msprogramacionvisitas.dto.ProgramacionVisitaDetalleResponse;
import com.fieldops.msprogramacionvisitas.dto.ProgramacionVisitaRequest;
import com.fieldops.msprogramacionvisitas.dto.ProgramacionVisitaResponse;
import com.fieldops.msprogramacionvisitas.exception.IntegrationException;
import com.fieldops.msprogramacionvisitas.exception.ResourceNotFoundException;
import com.fieldops.msprogramacionvisitas.model.ProgramacionVisitaDetalle;
import com.fieldops.msprogramacionvisitas.model.ProgramacionVisita;
import com.fieldops.msprogramacionvisitas.repository.ProgramacionVisitaRepository;
import com.fieldops.msprogramacionvisitas.feign.SolicitudesClient;
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
public class ProgramacionVisitaService {

    private final ProgramacionVisitaRepository repository;
    private final SolicitudesClient solicitudesClient;

    @Transactional
    public ProgramacionVisitaResponse create(ProgramacionVisitaRequest request) {
        log.info("Creando recurso en ms-programacion-visitas nombre={} estado={}", request.getNombre(), request.getEstado());
        try {
        if (request.getReferenciaExternaId() != null) {
            try {
                solicitudesClient.getSolicitudById(request.getReferenciaExternaId());
                log.info("Dependencia validada con SolicitudesClient para referenciaExternaId={}", request.getReferenciaExternaId());
            } catch (Exception ex) {
                log.error("Error validando dependencia remota con SolicitudesClient. referenciaExternaId={}", request.getReferenciaExternaId(), ex);
                throw new IntegrationException("Error al validar datos en microservicio dependiente", ex);
            }
        }
            ProgramacionVisita entity = ProgramacionVisita.builder()
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .estado(request.getEstado())
                    .referenciaExternaId(request.getReferenciaExternaId())
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            List<String> obs = request.getObservaciones() == null ? Collections.emptyList() : request.getObservaciones();
            obs.forEach(item -> entity.addDetalle(new ProgramacionVisitaDetalle(null, item, LocalDateTime.now(), null)));

            ProgramacionVisita saved = repository.save(entity);
            log.info("Recurso creado correctamente id={}", saved.getId());
            return toResponse(saved);
        } catch (IntegrationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error creando recurso en ms-programacion-visitas", ex);
            throw ex;
        }
    }

    public List<ProgramacionVisitaResponse> findAll() {
        log.info("Listando recursos de ms-programacion-visitas");
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public ProgramacionVisitaResponse findById(Long id) {
        log.info("Buscando recurso id={} en ms-programacion-visitas", id);
        return toResponse(getById(id));
    }

    @Transactional
    public ProgramacionVisitaResponse update(Long id, ProgramacionVisitaRequest request) {
        log.info("Actualizando recurso id={} en ms-programacion-visitas", id);
        ProgramacionVisita entity = getById(id);
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setEstado(request.getEstado());
        entity.setReferenciaExternaId(request.getReferenciaExternaId());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando recurso id={} en ms-programacion-visitas", id);
        repository.delete(getById(id));
    }

    private ProgramacionVisita getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));
    }

    private ProgramacionVisitaResponse toResponse(ProgramacionVisita entity) {
        return ProgramacionVisitaResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .referenciaExternaId(entity.getReferenciaExternaId())
                .fechaCreacion(entity.getFechaCreacion())
                .detalles(entity.getDetalles().stream().map(d -> ProgramacionVisitaDetalleResponse.builder()
                        .id(d.getId())
                        .observacion(d.getObservacion())
                        .fechaRegistro(d.getFechaRegistro())
                        .build()).toList())
                .build();
    }
}