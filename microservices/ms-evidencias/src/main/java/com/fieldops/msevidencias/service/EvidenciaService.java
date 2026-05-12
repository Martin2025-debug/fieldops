package com.fieldops.msevidencias.service;

import com.fieldops.msevidencias.dto.EvidenciaDetalleResponse;
import com.fieldops.msevidencias.dto.EvidenciaRequest;
import com.fieldops.msevidencias.dto.EvidenciaResponse;
import com.fieldops.msevidencias.exception.IntegrationException;
import com.fieldops.msevidencias.exception.ResourceNotFoundException;
import com.fieldops.msevidencias.model.EvidenciaDetalle;
import com.fieldops.msevidencias.model.Evidencia;
import com.fieldops.msevidencias.repository.EvidenciaRepository;
import com.fieldops.msevidencias.feign.AsignacionesClient;
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
public class EvidenciaService {

    private final EvidenciaRepository repository;
    private final AsignacionesClient asignacionesClient;

    @Transactional
    public EvidenciaResponse create(EvidenciaRequest request) {
        log.info("Creando recurso en ms-evidencias nombre={} estado={}", request.getNombre(), request.getEstado());
        try {
        if (request.getReferenciaExternaId() != null) {
            try {
                asignacionesClient.getAsignacionById(request.getReferenciaExternaId());
                log.info("Dependencia validada con AsignacionesClient para referenciaExternaId={}", request.getReferenciaExternaId());
            } catch (Exception ex) {
                log.error("Error validando dependencia remota con AsignacionesClient. referenciaExternaId={}", request.getReferenciaExternaId(), ex);
                throw new IntegrationException("Error al validar datos en microservicio dependiente", ex);
            }
        }
            Evidencia entity = Evidencia.builder()
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .estado(request.getEstado())
                    .referenciaExternaId(request.getReferenciaExternaId())
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            List<String> obs = request.getObservaciones() == null ? Collections.emptyList() : request.getObservaciones();
            obs.forEach(item -> entity.addDetalle(new EvidenciaDetalle(null, item, LocalDateTime.now(), null)));

            Evidencia saved = repository.save(entity);
            log.info("Recurso creado correctamente id={}", saved.getId());
            return toResponse(saved);
        } catch (IntegrationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error creando recurso en ms-evidencias", ex);
            throw ex;
        }
    }

    public List<EvidenciaResponse> findAll() {
        log.info("Listando recursos de ms-evidencias");
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public EvidenciaResponse findById(Long id) {
        log.info("Buscando recurso id={} en ms-evidencias", id);
        return toResponse(getById(id));
    }

    @Transactional
    public EvidenciaResponse update(Long id, EvidenciaRequest request) {
        log.info("Actualizando recurso id={} en ms-evidencias", id);
        Evidencia entity = getById(id);
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setEstado(request.getEstado());
        entity.setReferenciaExternaId(request.getReferenciaExternaId());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando recurso id={} en ms-evidencias", id);
        repository.delete(getById(id));
    }

    private Evidencia getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));
    }

    private EvidenciaResponse toResponse(Evidencia entity) {
        return EvidenciaResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .referenciaExternaId(entity.getReferenciaExternaId())
                .fechaCreacion(entity.getFechaCreacion())
                .detalles(entity.getDetalles().stream().map(d -> EvidenciaDetalleResponse.builder()
                        .id(d.getId())
                        .observacion(d.getObservacion())
                        .fechaRegistro(d.getFechaRegistro())
                        .build()).toList())
                .build();
    }
}