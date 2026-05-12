package com.fieldops.mssoporteremoto.service;

import com.fieldops.mssoporteremoto.dto.CasoSoporteDetalleResponse;
import com.fieldops.mssoporteremoto.dto.CasoSoporteRequest;
import com.fieldops.mssoporteremoto.dto.CasoSoporteResponse;
import com.fieldops.mssoporteremoto.exception.IntegrationException;
import com.fieldops.mssoporteremoto.exception.ResourceNotFoundException;
import com.fieldops.mssoporteremoto.model.CasoSoporteDetalle;
import com.fieldops.mssoporteremoto.model.CasoSoporte;
import com.fieldops.mssoporteremoto.repository.CasoSoporteRepository;
import com.fieldops.mssoporteremoto.feign.SolicitudesClient;
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
public class CasoSoporteService {

    private final CasoSoporteRepository repository;
    private final SolicitudesClient solicitudesClient;

    @Transactional
    public CasoSoporteResponse create(CasoSoporteRequest request) {
        log.info("Creando recurso en ms-soporte-remoto nombre={} estado={}", request.getNombre(), request.getEstado());
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
            CasoSoporte entity = CasoSoporte.builder()
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .estado(request.getEstado())
                    .referenciaExternaId(request.getReferenciaExternaId())
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            List<String> obs = request.getObservaciones() == null ? Collections.emptyList() : request.getObservaciones();
            obs.forEach(item -> entity.addDetalle(new CasoSoporteDetalle(null, item, LocalDateTime.now(), null)));

            CasoSoporte saved = repository.save(entity);
            log.info("Recurso creado correctamente id={}", saved.getId());
            return toResponse(saved);
        } catch (IntegrationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error creando recurso en ms-soporte-remoto", ex);
            throw ex;
        }
    }

    public List<CasoSoporteResponse> findAll() {
        log.info("Listando recursos de ms-soporte-remoto");
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public CasoSoporteResponse findById(Long id) {
        log.info("Buscando recurso id={} en ms-soporte-remoto", id);
        return toResponse(getById(id));
    }

    @Transactional
    public CasoSoporteResponse update(Long id, CasoSoporteRequest request) {
        log.info("Actualizando recurso id={} en ms-soporte-remoto", id);
        CasoSoporte entity = getById(id);
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setEstado(request.getEstado());
        entity.setReferenciaExternaId(request.getReferenciaExternaId());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando recurso id={} en ms-soporte-remoto", id);
        repository.delete(getById(id));
    }

    private CasoSoporte getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));
    }

    private CasoSoporteResponse toResponse(CasoSoporte entity) {
        return CasoSoporteResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .referenciaExternaId(entity.getReferenciaExternaId())
                .fechaCreacion(entity.getFechaCreacion())
                .detalles(entity.getDetalles().stream().map(d -> CasoSoporteDetalleResponse.builder()
                        .id(d.getId())
                        .observacion(d.getObservacion())
                        .fechaRegistro(d.getFechaRegistro())
                        .build()).toList())
                .build();
    }
}