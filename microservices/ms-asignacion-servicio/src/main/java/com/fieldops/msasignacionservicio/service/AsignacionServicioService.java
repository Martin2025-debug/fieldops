package com.fieldops.msasignacionservicio.service;

import com.fieldops.msasignacionservicio.dto.AsignacionServicioDetalleResponse;
import com.fieldops.msasignacionservicio.dto.AsignacionServicioRequest;
import com.fieldops.msasignacionservicio.dto.AsignacionServicioResponse;
import com.fieldops.msasignacionservicio.exception.IntegrationException;
import com.fieldops.msasignacionservicio.exception.ResourceNotFoundException;
import com.fieldops.msasignacionservicio.model.AsignacionServicioDetalle;
import com.fieldops.msasignacionservicio.model.AsignacionServicio;
import com.fieldops.msasignacionservicio.repository.AsignacionServicioRepository;
import com.fieldops.msasignacionservicio.feign.TecnicosClient;
import com.fieldops.msasignacionservicio.feign.ProgramacionVisitasClient;
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
public class AsignacionServicioService {

    private final AsignacionServicioRepository repository;
    private final TecnicosClient tecnicosClient;
    private final ProgramacionVisitasClient programacionVisitasClient;

    @Transactional
    public AsignacionServicioResponse create(AsignacionServicioRequest request) {
        log.info("Creando recurso en ms-asignacion-servicio nombre={} estado={}", request.getNombre(), request.getEstado());
        try {
        if (request.getReferenciaExternaId() != null) {
            try {
                tecnicosClient.getTecnicoById(request.getReferenciaExternaId());
                log.info("Dependencia validada con TecnicosClient para referenciaExternaId={}", request.getReferenciaExternaId());
            } catch (Exception ex) {
                log.error("Error validando dependencia remota con TecnicosClient. referenciaExternaId={}", request.getReferenciaExternaId(), ex);
                throw new IntegrationException("Error al validar datos en microservicio dependiente", ex);
            }
        }        if (request.getReferenciaExternaId() != null) {
            try {
                programacionVisitasClient.getProgramacionById(request.getReferenciaExternaId());
                log.info("Dependencia validada con ProgramacionVisitasClient para referenciaExternaId={}", request.getReferenciaExternaId());
            } catch (Exception ex) {
                log.error("Error validando dependencia remota con ProgramacionVisitasClient. referenciaExternaId={}", request.getReferenciaExternaId(), ex);
                throw new IntegrationException("Error al validar datos en microservicio dependiente", ex);
            }
        }
            AsignacionServicio entity = AsignacionServicio.builder()
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .estado(request.getEstado())
                    .referenciaExternaId(request.getReferenciaExternaId())
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            List<String> obs = request.getObservaciones() == null ? Collections.emptyList() : request.getObservaciones();
            obs.forEach(item -> entity.addDetalle(new AsignacionServicioDetalle(null, item, LocalDateTime.now(), null)));

            AsignacionServicio saved = repository.save(entity);
            log.info("Recurso creado correctamente id={}", saved.getId());
            return toResponse(saved);
        } catch (IntegrationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error creando recurso en ms-asignacion-servicio", ex);
            throw ex;
        }
    }

    public List<AsignacionServicioResponse> findAll() {
        log.info("Listando recursos de ms-asignacion-servicio");
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public AsignacionServicioResponse findById(Long id) {
        log.info("Buscando recurso id={} en ms-asignacion-servicio", id);
        return toResponse(getById(id));
    }

    @Transactional
    public AsignacionServicioResponse update(Long id, AsignacionServicioRequest request) {
        log.info("Actualizando recurso id={} en ms-asignacion-servicio", id);
        AsignacionServicio entity = getById(id);
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setEstado(request.getEstado());
        entity.setReferenciaExternaId(request.getReferenciaExternaId());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando recurso id={} en ms-asignacion-servicio", id);
        repository.delete(getById(id));
    }

    private AsignacionServicio getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));
    }

    private AsignacionServicioResponse toResponse(AsignacionServicio entity) {
        return AsignacionServicioResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .referenciaExternaId(entity.getReferenciaExternaId())
                .fechaCreacion(entity.getFechaCreacion())
                .detalles(entity.getDetalles().stream().map(d -> AsignacionServicioDetalleResponse.builder()
                        .id(d.getId())
                        .observacion(d.getObservacion())
                        .fechaRegistro(d.getFechaRegistro())
                        .build()).toList())
                .build();
    }
}