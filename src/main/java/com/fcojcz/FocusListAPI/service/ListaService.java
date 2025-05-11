package com.fcojcz.FocusListAPI.service;

import com.fcojcz.FocusListAPI.model.dto.lista.ListaCreateDTO;
import com.fcojcz.FocusListAPI.model.dto.lista.ListaDeleteDTO;
import com.fcojcz.FocusListAPI.model.dto.lista.ListaResponseDTO;
import com.fcojcz.FocusListAPI.model.dto.lista.ListaUpdateDTO;
import com.fcojcz.FocusListAPI.model.dto.tarea.TareaResponseDTO;
import com.fcojcz.FocusListAPI.model.entity.Lista;
import com.fcojcz.FocusListAPI.model.entity.Tarea;
import com.fcojcz.FocusListAPI.repository.ListaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class ListaService {

    @Autowired
    ListaRepository listaRepository;

    private static final Logger logger = LoggerFactory.getLogger(ListaService.class);

    public Lista save(ListaCreateDTO listaCreateDTO) {
        try {
            if (listaRepository.existsByNombre(listaCreateDTO.getNombre())) {
                logger.error("Ya existe una lista con el nombre: {}", listaCreateDTO.getNombre());
                throw new RuntimeException("Ya existe una lista con el nombre: " + listaCreateDTO.getNombre());
            }
            logger.info("Creando la lista a partir del DTO: {}", listaCreateDTO.getNombre());
            LocalDate fechaCreacion = LocalDate.now();
            Lista lista = Lista.builder()
                    .nombre(listaCreateDTO.getNombre())
                    .fechaCreacion(fechaCreacion)
                    .build();
            logger.info("Guardando la lista: {}", listaCreateDTO.getNombre());
            return listaRepository.save(lista);
        } catch (Exception e) {
            logger.error("Error al crear la lista: {}", e.getMessage());
            throw new RuntimeException("Error al crear la lista: " + e.getMessage());
        }

    }

    public Lista update(ListaUpdateDTO listaUpdateDTO) {
        try {
            logger.info("Buscando lista a actualizar con ID: {}", listaUpdateDTO.getId());
            Lista listaToUpdate = listaRepository.findById(listaUpdateDTO.getId()).orElse(null);

            if (listaToUpdate != null) {
                listaToUpdate.setNombre(listaUpdateDTO.getNombre());
            }

            logger.info("Actualizando la lista con ID: {}", listaUpdateDTO.getId());
            return listaRepository.save(listaToUpdate);
        } catch (Exception e) {
            logger.error("Error al actualizar la lista: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar la lista: " + e.getMessage());
        }
    }

    public boolean delete(ListaDeleteDTO listaDeleteDTO) {
        try {
            Optional<Lista> listaOptional = listaRepository.findById(listaDeleteDTO.getId());
            if (listaOptional.isPresent()) {
                logger.info("Borrando la lista con ID: {}", listaDeleteDTO.getId());
                listaRepository.deleteById(listaDeleteDTO.getId());
                return true;
            }
            logger.error("No se ha encontrado la lista con ID: {}", listaDeleteDTO.getId());
            return false;
        } catch (Exception e) {
            logger.error("Error al borrar la lista con ID: {}", listaDeleteDTO.getId(), e.getMessage());
            throw new RuntimeException("Error al borrar la lista.\n" + e.getMessage());
        }
    }

    public Page<Lista> findAllByUsuario(Pageable pageable, String username) {
        try {
            logger.info("Buscando todas las listas del usuario");

            return listaRepository.findAllByUsuario(
                    PageRequest.of(
                            pageable.getPageNumber() > 0
                                    ? pageable.getPageNumber()
                                    : 0,
                            pageable.getPageSize() > 0
                                    ? pageable.getPageSize()
                                    : 10,
                            pageable.getSort()
                    ), username
            );
        } catch (Exception e) {
            logger.error("Error al buscar todas las listas del usuario\n" + e.getMessage());
            throw new RuntimeException("Error al buscar todas las listas del usuario\n" + e.getMessage());
        }
    }

    public Lista loadByName(String nombre) {
        try {
            Lista result = listaRepository.findByName(nombre).orElse(null);

            if (result == null) {
                logger.error("No se ha encontrado la lista con el nombre: {}", nombre);
            } else {
                logger.info("Buscando la lista con el nombre: {}", nombre);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar la lista con el nombre: {}", e.getMessage());
            throw new RuntimeException("Error al buscar la lista por nombre: " + e.getMessage());
        }
    }

    public boolean existsByNombre(String nombre) {
        try {
            logger.info("Buscando la lista con el nombre: {}", nombre);
            return listaRepository.existsByNombre(nombre);
        } catch (Exception e) {
            logger.error("Error al buscar la lista con el nombre: {}", e.getMessage());
            throw new RuntimeException("Error al buscar la lista por nombre: " + e.getMessage());
        }
    }

    public ListaResponseDTO mapToListaResponseDTO(Lista lista) {
        try {
            logger.info("Mapeando la lista con ID: {}", lista.getId());

            Set<TareaResponseDTO> tareas = new HashSet<>();
            // TODO: Mapear las tareas cuando tengamos el repository y servicio de tareas

            return ListaResponseDTO.builder()
                    .id(lista.getId())
                    .nombre(lista.getNombre())
                    .fechaCreacion(lista.getFechaCreacion())
                    .idUsuario(lista.getUsuario().getId())
                    .tareas(tareas)
                    .build();
        } catch (Exception e) {
            logger.error("Error al mapear la lista con ID: {}", e.getMessage());
            throw new RuntimeException("Error al mapear la lista\n" + e.getMessage());
        }

    }

}
