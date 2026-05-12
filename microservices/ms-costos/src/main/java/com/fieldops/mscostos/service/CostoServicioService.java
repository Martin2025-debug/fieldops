package com.fieldops.mscostos.service;

import com.fieldops.mscostos.dto.CostoServicioDetalleResponse;
import com.fieldops.mscostos.dto.CostoServicioRequest;
import com.fieldops.mscostos.dto.CostoServicioResponse;
import com.fieldops.mscostos.exception.IntegrationException;
import com.fieldops.mscostos.exception.ResourceNotFoundException;
import com.fieldops.mscostos.model.CostoServicioDetalle;
import com.fieldops.mscostos.model.CostoServicio;
import com.fieldops.mscostos.repository.CostoServicioRepository;
import com.fieldops.mscostos.feign.AsignacionesClient;
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
public class CostoServicioService {

    private final CostoServicioRepository repository;
    private final AsignacionesClient asignacionesClient;

    @Transactional
    public CostoServicioResponse create(CostoServicioRequest request) {
        log.info("Creando recurso en ms-costos nombre={} estado={}", request.getNombre(), request.getEstado());
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
            CostoServicio entity = CostoServicio.builder()
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .estado(request.getEstado())
                    .referenciaExternaId(request.getReferenciaExternaId())
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            List<String> obs = request.getObservaciones() == null ? Collections.emptyList() : request.getObservaciones();
            obs.forEach(item -> entity.addDetalle(new CostoServicioDetalle(null, item, LocalDateTime.now(), null)));

            CostoServicio saved = repository.save(entity);
            log.info("Recurso creado correctamente id={}", saved.getId());
            return toResponse(saved);
        } catch (IntegrationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error creando recurso en ms-costos", ex);
            throw ex;
        }
    }

    public List<CostoServicioResponse> findAll() {
        log.info("Listando recursos de ms-costos");
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public CostoServicioResponse findById(Long id) {
        log.info("Buscando recurso id={} en ms-costos", id);
        return toResponse(getById(id));
    }

    @Transactional
    public CostoServicioResponse update(Long id, CostoServicioRequest request) {
        log.info("Actualizando recurso id={} en ms-costos", id);
        CostoServicio entity = getById(id);
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setEstado(request.getEstado());
        entity.setReferenciaExternaId(request.getReferenciaExternaId());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando recurso id={} en ms-costos", id);
        repository.delete(getById(id));
    }

    private CostoServicio getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));
    }

    private CostoServicioResponse toResponse(CostoServicio entity) {
        return CostoServicioResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .referenciaExternaId(entity.getReferenciaExternaId())
                .fechaCreacion(entity.getFechaCreacion())
                .detalles(entity.getDetalles().stream().map(d -> CostoServicioDetalleResponse.builder()
                        .id(d.getId())
                        .observacion(d.getObservacion())
                        .fechaRegistro(d.getFechaRegistro())
                        .build()).toList())
                .build();
    }
}