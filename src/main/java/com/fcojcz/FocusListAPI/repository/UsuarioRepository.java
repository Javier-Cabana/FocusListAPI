package com.fcojcz.FocusListAPI.repository;

import com.fcojcz.FocusListAPI.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {

    /**
     * Encontrar un usuario por su email
     * @param email String email del usuario
     * @return Devuelve el usuario si lo encuentra, sino devuelve vacío.
     */
    Optional<Usuario> findByEmail(String email);

    /**
     * Encontrar un usuario por su username
     * @param username String username del usuario
     * @return Devuelve el usuario si lo encuentra, sino devuelve vacío.
     */
    Optional<Usuario> findByUsername(String username);

    /**
     * Verificar si el email ya existe
     * @param email String email a verificar
     * @return Devuelve true si lo encuentra, sino devuelve false
     */
    boolean existsByEmail(String email);

    /**
     * Devuelve todos los usuarios
     * @param pageable Información de paginación
     * @return Una página con todos los usuarios
     */
    Page<Usuario> findAll(Pageable pageable);
}
