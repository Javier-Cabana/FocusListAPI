package com.fcojcz.FocusListAPI.service;

import com.fcojcz.FocusListAPI.model.dto.lista.*;
import com.fcojcz.FocusListAPI.model.entity.Lista;
import com.fcojcz.FocusListAPI.model.entity.Tarea;
import com.fcojcz.FocusListAPI.model.entity.Usuario;
import com.fcojcz.FocusListAPI.repository.ListaRepository;
import com.fcojcz.FocusListAPI.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ListaService {

    @Autowired
    ListaRepository listaRepository;
    @Autowired
    UsuarioRepository usuarioRepository;

    private static final Logger logger = LoggerFactory.getLogger(ListaService.class);

    public Lista save(ListaCreateDTO listaCreateDTO) {
        try {
            Usuario usuario = usuarioRepository.findById(listaCreateDTO.getIdUsuario()).orElse(null);

            if (listaRepository.existsByNombreAndUsuario(listaCreateDTO.getNombre(), usuario)) {
                logger.error("Ya existe una lista con el nombre: {}", listaCreateDTO.getNombre());
                throw new RuntimeException("Ya existe una lista con el nombre: " + listaCreateDTO.getNombre());
            }
            logger.info("Creando la lista a partir del DTO: {}", listaCreateDTO.getNombre());
            Lista lista = Lista.builder()
                    .nombre(listaCreateDTO.getNombre())
                    .fechaCreacion(LocalDate.now())
                    .usuario(usuario)
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

            if (listaRepository.existsByNombreAndUsuario(listaUpdateDTO.getNombre(),
                    usuarioRepository.findById(listaUpdateDTO.getIdUsuario()).orElse(null))) {
                logger.error("Ya existe una lista con el nombre: {}", listaUpdateDTO.getNombre());
                throw new RuntimeException("Ya existe una lista con el nombre: " + listaUpdateDTO.getNombre());
            }

            listaToUpdate.setNombre(listaUpdateDTO.getNombre());

            logger.info("Actualizando la lista con ID: {}", listaUpdateDTO.getId());
            return listaRepository.save(listaToUpdate);
        } catch (Exception e) {
            logger.error("Error al actualizar la lista: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar la lista: " + e.getMessage());
        }
    }

    @Transactional
    public boolean delete(ListaDeleteDTO listaDeleteDTO) {
        try {
            Lista lista = listaRepository.findById(listaDeleteDTO.getId()).orElse(null);
            if (lista != null) {
                logger.info("Borrando la lista con ID: {}", listaDeleteDTO.getId());
                Usuario usuario = lista.getUsuario();
                usuario.getListas().remove(lista);
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

            Optional<Usuario> result = usuarioRepository.findByUsername(username);

            if (result.isEmpty()) {
                logger.error("No se ha encontrado el usuario con username: {}", username);
                throw new RuntimeException("No se ha encontrado el usuario con username: " + username);
            }

            return listaRepository.findAllByUsuario(
                    PageRequest.of(
                            pageable.getPageNumber() > 0
                                    ? pageable.getPageNumber()
                                    : 0,
                            pageable.getPageSize() > 0
                                    ? pageable.getPageSize()
                                    : 10,
                            pageable.getSort()
                    ), result.get()
            );
        } catch (Exception e) {
            logger.error("Error al buscar todas las listas del usuario\n" + e.getMessage());
            throw new RuntimeException("Error al buscar todas las listas del usuario\n" + e.getMessage());
        }
    }

    public Lista loadByNameAndUsuario(ListaGetDTO listaGetDTO) {
        try {
            logger.info("Buscando la lista con el nombre: {}", listaGetDTO.getNombre());

            Optional<Lista> result = listaRepository.findByNombreAndUsuario(listaGetDTO.getNombre(),
                    usuarioRepository.findById(listaGetDTO.getIdUsuario()).orElse(null));

            if (result.isEmpty()) {
                logger.error("No se ha encontrado la lista con el nombre: {}", listaGetDTO.getNombre());
                throw new RuntimeException("No se ha encontrado la lista con el nombre: " + listaGetDTO.getNombre());
            } else {
                return result.get();
            }

        } catch (Exception e) {
            logger.error("Error al buscar la lista con el nombre: {}", e.getMessage());
            throw new RuntimeException("Error al buscar la lista por nombre: " + e.getMessage());
        }
    }

    public ListaResponseDTO mapToListaResponseDTO(Lista lista) {
        try {
            logger.info("Mapeando la lista con ID: {}", lista.getId());

            Set<UUID> tareas = new HashSet<>();
            if (lista.getTareas() != null) {
                for (Tarea tarea : lista.getTareas()) {
                    tareas.add(tarea.getId());
                }
            }

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
