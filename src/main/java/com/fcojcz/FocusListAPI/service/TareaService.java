package com.fcojcz.FocusListAPI.service;

import com.fcojcz.FocusListAPI.model.dto.tarea.TareaCreateDTO;
import com.fcojcz.FocusListAPI.model.dto.tarea.TareaDeleteDTO;
import com.fcojcz.FocusListAPI.model.dto.tarea.TareaResponseDTO;
import com.fcojcz.FocusListAPI.model.dto.tarea.TareaUpdateDTO;
import com.fcojcz.FocusListAPI.model.entity.Lista;
import com.fcojcz.FocusListAPI.model.entity.Tarea;
import com.fcojcz.FocusListAPI.repository.ListaRepository;
import com.fcojcz.FocusListAPI.repository.TareaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TareaService {

    @Autowired
    TareaRepository tareaRepository;

    @Autowired
    ListaRepository listaRepository;

    private static final Logger logger = LoggerFactory.getLogger(TareaService.class);

    public Tarea save(TareaCreateDTO tareaCreateDTO) {
        try {
            Lista lista = listaRepository.findById(tareaCreateDTO.getIdLista()).orElse(null);

            if (lista == null) {
                logger.error("No se ha encontrado la lista con ID: {}", tareaCreateDTO.getIdLista());
                throw new RuntimeException("No se ha encontrado la lista con ID: " + tareaCreateDTO.getIdLista());
            }
            logger.info("Creando la tarea a partir del DTO: {}", tareaCreateDTO.getTitulo());
            Tarea tarea = Tarea.builder()
                    .titulo(tareaCreateDTO.getTitulo())
                    .descripcion(tareaCreateDTO.getDescripcion())
                    .completada(false)
                    .fechaCreacion(LocalDateTime.now())
                    .fechaVencimiento(tareaCreateDTO.getFechaVencimiento())
                    .lista(lista)
                    .etiqueta(null) //TODO: Crear en el servicio/repositorio de etiqueta un metodo para referenciar una etiqueta de la BBDD
                    .build();       //TODO quedaría algo tal que así .etiqueta(etiquetaRepository.findById(tareaCreateDTO.getIdEtiqueta()).orElse(null))
            logger.info("Guardando la tarea: {}", tareaCreateDTO.getTitulo());
            return tareaRepository.save(tarea);
        } catch (RuntimeException e) {
            logger.error("Error al guardar la tarea: {}", e.getMessage());
            throw new RuntimeException("Error al guardar la tarea\n" + e.getMessage());
        }
    }

    public Tarea update(TareaUpdateDTO tareaUpdateDTO) {
        try {
            logger.info("Buscando la tarea a actualizar con ID: '{}'", tareaUpdateDTO.getId());
            Tarea tareaToUpdate = tareaRepository.findById(tareaUpdateDTO.getId()).orElse(null);

            tareaToUpdate.setTitulo(tareaUpdateDTO.getTitulo());
            tareaToUpdate.setDescripcion(tareaUpdateDTO.getDescripcion());
            tareaToUpdate.setCompletada(tareaUpdateDTO.getCompletada());
            tareaToUpdate.setFechaVencimiento(tareaUpdateDTO.getFechaVencimiento());
            tareaToUpdate.setEtiqueta(null); //TODO: Crear metodo para referenciar una etiqueta de la BBDD

            logger.info("Actualizando la tarea: {}", tareaToUpdate);
            return tareaRepository.save(tareaToUpdate);
        } catch (Exception e) {
            logger.error("Error al actualizar la tarea: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar la tarea\n" + e.getMessage());
        }
    }

    @Transactional
    public boolean delete(TareaDeleteDTO tareaDeleteDTO) {
        try {
            Tarea tarea = tareaRepository.findById(tareaDeleteDTO.getId()).orElse(null);
            if (tarea != null) {
                logger.info("Borrando la tarea: {}", tarea.getTitulo());
                Lista lista = tarea.getLista();
                lista.getTareas().remove(tarea);
                return true;
            }
            logger.error("No se ha encontrado la tarea con ID: {}", tareaDeleteDTO.getId());
            return false;
        } catch (Exception e) {
            logger.error("Error al borrar la tarea: {}", e.getMessage());
            throw new RuntimeException("Error al borrar la tarea\n" + e.getMessage());
        }
    }

    public Page<Tarea> loadAllByLista(Pageable pageable, UUID idLista) {
        try {
            logger.info("Buscando todas las tareas de la lista");

            Optional<Lista> result = listaRepository.findById(idLista);

            if (result.isEmpty()) {
                logger.error("No se ha encontrado la lista con ID: {}", idLista);
                throw new RuntimeException("No se ha encontrado la lista con ID: " + idLista);
            }

            return tareaRepository.findAllByLista(
                    PageRequest.of(
                            pageable.getPageNumber() > 0
                                    ? pageable.getPageNumber()
                                    : 0,
                            pageable.getPageSize() > 0
                                    ? pageable.getPageSize()
                                    : 10,
                            pageable.getSort()
                    ),
                    result.get()
            );
        } catch (RuntimeException e) {
            logger.error("Error al buscar todas las tareas de la lista\n" + e.getMessage());
            throw new RuntimeException("Error al buscar todas las tareas de la lista\n" + e.getMessage());
        }
    }

    /**
     * Método para obtener una única tarea
     * @param id id de la tarea
     * @return Tarea
     */
    public Tarea loadById(UUID id) {
        try {
            logger.info("Buscando la tarea con id: {}", id);
            return tareaRepository.findById(id).orElse(null);
        } catch (Exception e) {
            logger.error("Error al buscar la tarea con id: {}", e.getMessage());
            throw new RuntimeException("Error al buscar la tarea con id\n" + e.getMessage());
        }
    }

    public TareaResponseDTO mapToTareaResponseDTO(Tarea tarea) {
        try {
            logger.info("Mapeando la tarea con id: {}", tarea.getId());

            return TareaResponseDTO.builder()
                    .id(tarea.getId())
                    .titulo(tarea.getTitulo())
                    .descripcion(tarea.getDescripcion())
                    .completada(tarea.getCompletada())
                    .fechaCreacion(tarea.getFechaCreacion())
                    .fechaVencimiento(tarea.getFechaVencimiento())
                    .idLista(tarea.getLista().getId())
                    .idEtiqueta(tarea.getEtiqueta().getId_etiqueta())
                    .build();
        } catch (Exception e) {
            logger.error("Error al mapear la tarea: {}", e.getMessage());
            throw new RuntimeException("Error al mapear la tarea\n" + e.getMessage());
        }
    }
}