package com.fieldops.mssolicitudes.service;

import com.fieldops.mssolicitudes.dto.SolicitudDetalleResponse;
import com.fieldops.mssolicitudes.dto.SolicitudRequest;
import com.fieldops.mssolicitudes.dto.SolicitudResponse;
import com.fieldops.mssolicitudes.exception.IntegrationException;
import com.fieldops.mssolicitudes.exception.ResourceNotFoundException;
import com.fieldops.mssolicitudes.model.SolicitudDetalle;
import com.fieldops.mssolicitudes.model.Solicitud;
import com.fieldops.mssolicitudes.repository.SolicitudRepository;
import com.fieldops.mssolicitudes.feign.ClientesClient;
import com.fieldops.mssolicitudes.feign.TipoServicioClient;
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
public class SolicitudService {

    private final SolicitudRepository repository;
    private final ClientesClient clientesClient;
    private final TipoServicioClient tipoServicioClient;

    @Transactional
    public SolicitudResponse create(SolicitudRequest request) {
        log.info("Creando recurso en ms-solicitudes nombre={} estado={}", request.getNombre(), request.getEstado());
        try {
        if (request.getReferenciaExternaId() != null) {
            try {
                clientesClient.getClienteById(request.getReferenciaExternaId());
                log.info("Dependencia validada con ClientesClient para referenciaExternaId={}", request.getReferenciaExternaId());
            } catch (Exception ex) {
                log.error("Error validando dependencia remota con ClientesClient. referenciaExternaId={}", request.getReferenciaExternaId(), ex);
                throw new IntegrationException("Error al validar datos en microservicio dependiente", ex);
            }
        }        if (request.getReferenciaExternaId() != null) {
            try {
                tipoServicioClient.getTipoServicioById(request.getReferenciaExternaId());
                log.info("Dependencia validada con TipoServicioClient para referenciaExternaId={}", request.getReferenciaExternaId());
            } catch (Exception ex) {
                log.error("Error validando dependencia remota con TipoServicioClient. referenciaExternaId={}", request.getReferenciaExternaId(), ex);
                throw new IntegrationException("Error al validar datos en microservicio dependiente", ex);
            }
        }
            Solicitud entity = Solicitud.builder()
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .estado(request.getEstado())
                    .referenciaExternaId(request.getReferenciaExternaId())
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            List<String> obs = request.getObservaciones() == null ? Collections.emptyList() : request.getObservaciones();
            obs.forEach(item -> entity.addDetalle(new SolicitudDetalle(null, item, LocalDateTime.now(), null)));

            Solicitud saved = repository.save(entity);
            log.info("Recurso creado correctamente id={}", saved.getId());
            return toResponse(saved);
        } catch (IntegrationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error creando recurso en ms-solicitudes", ex);
            throw ex;
        }
    }

    public List<SolicitudResponse> findAll() {
        log.info("Listando recursos de ms-solicitudes");
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public SolicitudResponse findById(Long id) {
        log.info("Buscando recurso id={} en ms-solicitudes", id);
        return toResponse(getById(id));
    }

    @Transactional
    public SolicitudResponse update(Long id, SolicitudRequest request) {
        log.info("Actualizando recurso id={} en ms-solicitudes", id);
        Solicitud entity = getById(id);
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setEstado(request.getEstado());
        entity.setReferenciaExternaId(request.getReferenciaExternaId());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando recurso id={} en ms-solicitudes", id);
        repository.delete(getById(id));
    }

    private Solicitud getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));
    }

    private SolicitudResponse toResponse(Solicitud entity) {
        return SolicitudResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .referenciaExternaId(entity.getReferenciaExternaId())
                .fechaCreacion(entity.getFechaCreacion())
                .detalles(entity.getDetalles().stream().map(d -> SolicitudDetalleResponse.builder()
                        .id(d.getId())
                        .observacion(d.getObservacion())
                        .fechaRegistro(d.getFechaRegistro())
                        .build()).toList())
                .build();
    }
}