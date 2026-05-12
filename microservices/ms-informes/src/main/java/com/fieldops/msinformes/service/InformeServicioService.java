package com.fieldops.msinformes.service;

import com.fieldops.msinformes.dto.InformeServicioDetalleResponse;
import com.fieldops.msinformes.dto.InformeServicioRequest;
import com.fieldops.msinformes.dto.InformeServicioResponse;
import com.fieldops.msinformes.exception.IntegrationException;
import com.fieldops.msinformes.exception.ResourceNotFoundException;
import com.fieldops.msinformes.model.InformeServicioDetalle;
import com.fieldops.msinformes.model.InformeServicio;
import com.fieldops.msinformes.repository.InformeServicioRepository;
import com.fieldops.msinformes.feign.EvidenciasClient;
import com.fieldops.msinformes.feign.CostosClient;
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
public class InformeServicioService {

    private final InformeServicioRepository repository;
    private final EvidenciasClient evidenciasClient;
    private final CostosClient costosClient;

    @Transactional
    public InformeServicioResponse create(InformeServicioRequest request) {
        log.info("Creando recurso en ms-informes nombre={} estado={}", request.getNombre(), request.getEstado());
        try {
        if (request.getReferenciaExternaId() != null) {
            try {
                evidenciasClient.getEvidenciaById(request.getReferenciaExternaId());
                log.info("Dependencia validada con EvidenciasClient para referenciaExternaId={}", request.getReferenciaExternaId());
            } catch (Exception ex) {
                log.error("Error validando dependencia remota con EvidenciasClient. referenciaExternaId={}", request.getReferenciaExternaId(), ex);
                throw new IntegrationException("Error al validar datos en microservicio dependiente", ex);
            }
        }        if (request.getReferenciaExternaId() != null) {
            try {
                costosClient.getCostoById(request.getReferenciaExternaId());
                log.info("Dependencia validada con CostosClient para referenciaExternaId={}", request.getReferenciaExternaId());
            } catch (Exception ex) {
                log.error("Error validando dependencia remota con CostosClient. referenciaExternaId={}", request.getReferenciaExternaId(), ex);
                throw new IntegrationException("Error al validar datos en microservicio dependiente", ex);
            }
        }
            InformeServicio entity = InformeServicio.builder()
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .estado(request.getEstado())
                    .referenciaExternaId(request.getReferenciaExternaId())
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            List<String> obs = request.getObservaciones() == null ? Collections.emptyList() : request.getObservaciones();
            obs.forEach(item -> entity.addDetalle(new InformeServicioDetalle(null, item, LocalDateTime.now(), null)));

            InformeServicio saved = repository.save(entity);
            log.info("Recurso creado correctamente id={}", saved.getId());
            return toResponse(saved);
        } catch (IntegrationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error creando recurso en ms-informes", ex);
            throw ex;
        }
    }

    public List<InformeServicioResponse> findAll() {
        log.info("Listando recursos de ms-informes");
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public InformeServicioResponse findById(Long id) {
        log.info("Buscando recurso id={} en ms-informes", id);
        return toResponse(getById(id));
    }

    @Transactional
    public InformeServicioResponse update(Long id, InformeServicioRequest request) {
        log.info("Actualizando recurso id={} en ms-informes", id);
        InformeServicio entity = getById(id);
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setEstado(request.getEstado());
        entity.setReferenciaExternaId(request.getReferenciaExternaId());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando recurso id={} en ms-informes", id);
        repository.delete(getById(id));
    }

    private InformeServicio getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));
    }

    private InformeServicioResponse toResponse(InformeServicio entity) {
        return InformeServicioResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .referenciaExternaId(entity.getReferenciaExternaId())
                .fechaCreacion(entity.getFechaCreacion())
                .detalles(entity.getDetalles().stream().map(d -> InformeServicioDetalleResponse.builder()
                        .id(d.getId())
                        .observacion(d.getObservacion())
                        .fechaRegistro(d.getFechaRegistro())
                        .build()).toList())
                .build();
    }
}