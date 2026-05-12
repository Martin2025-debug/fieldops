package com.fieldops.mstiposervicio.service;

import com.fieldops.mstiposervicio.dto.TipoServicioDetalleResponse;
import com.fieldops.mstiposervicio.dto.TipoServicioRequest;
import com.fieldops.mstiposervicio.dto.TipoServicioResponse;
import com.fieldops.mstiposervicio.exception.IntegrationException;
import com.fieldops.mstiposervicio.exception.ResourceNotFoundException;
import com.fieldops.mstiposervicio.model.TipoServicioDetalle;
import com.fieldops.mstiposervicio.model.TipoServicio;
import com.fieldops.mstiposervicio.repository.TipoServicioRepository;
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
public class TipoServicioService {

    private final TipoServicioRepository repository;

    @Transactional
    public TipoServicioResponse create(TipoServicioRequest request) {
        log.info("Creando recurso en ms-tipo-servicio nombre={} estado={}", request.getNombre(), request.getEstado());
        try {

            TipoServicio entity = TipoServicio.builder()
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .estado(request.getEstado())
                    .referenciaExternaId(request.getReferenciaExternaId())
                    .fechaCreacion(LocalDateTime.now())
                    .build();

            List<String> obs = request.getObservaciones() == null ? Collections.emptyList() : request.getObservaciones();
            obs.forEach(item -> entity.addDetalle(new TipoServicioDetalle(null, item, LocalDateTime.now(), null)));

            TipoServicio saved = repository.save(entity);
            log.info("Recurso creado correctamente id={}", saved.getId());
            return toResponse(saved);
        } catch (IntegrationException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error("Error creando recurso en ms-tipo-servicio", ex);
            throw ex;
        }
    }

    public List<TipoServicioResponse> findAll() {
        log.info("Listando recursos de ms-tipo-servicio");
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    public TipoServicioResponse findById(Long id) {
        log.info("Buscando recurso id={} en ms-tipo-servicio", id);
        return toResponse(getById(id));
    }

    @Transactional
    public TipoServicioResponse update(Long id, TipoServicioRequest request) {
        log.info("Actualizando recurso id={} en ms-tipo-servicio", id);
        TipoServicio entity = getById(id);
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setEstado(request.getEstado());
        entity.setReferenciaExternaId(request.getReferenciaExternaId());
        return toResponse(repository.save(entity));
    }

    @Transactional
    public void delete(Long id) {
        log.info("Eliminando recurso id={} en ms-tipo-servicio", id);
        repository.delete(getById(id));
    }

    private TipoServicio getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Recurso no encontrado con id: " + id));
    }

    private TipoServicioResponse toResponse(TipoServicio entity) {
        return TipoServicioResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .estado(entity.getEstado())
                .referenciaExternaId(entity.getReferenciaExternaId())
                .fechaCreacion(entity.getFechaCreacion())
                .detalles(entity.getDetalles().stream().map(d -> TipoServicioDetalleResponse.builder()
                        .id(d.getId())
                        .observacion(d.getObservacion())
                        .fechaRegistro(d.getFechaRegistro())
                        .build()).toList())
                .build();
    }
}