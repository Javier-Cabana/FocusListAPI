package com.fcojcz.FocusListAPI.repository;

import com.fcojcz.FocusListAPI.model.entity.Lista;

import com.fcojcz.FocusListAPI.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ListaRepository extends JpaRepository<Lista, UUID> {

    /**
     * Devuelve todas las listas creadas por un usuario específico.
     * @param usuario Usuario propietario de las listas.
     * @param pageable Información de paginación
     * @return Página con todas las listas del usuario.
     */
    Page<Lista> findAllByUsuario(Pageable pageable, Usuario usuario);

    /**
     * Encontrar una lista por su nombre
     * @param nombre Nombre de la lista
     * @return Devuelve la lista si la encuentra, sino devuelve vacío.
     */
    Optional<Lista> findByNombreAndUsuario(String nombre, Usuario usuario);

    /**
     * Comprueba si existe una lista con ese nombre
     * @param nombre Nombre de la lista
     * @return Devuelve true si la lista existe, sino devuelve false
     */
    boolean existsByNombreAndUsuario(String nombre, Usuario usuario);
}
