package com.fcojcz.FocusListAPI.repository;

import com.fcojcz.FocusListAPI.model.entity.Lista;
import com.fcojcz.FocusListAPI.model.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ListaRepository extends JpaRepository<Lista, UUID> {

    /**
     * Devuelve todas las listas creadas por un usuario específico.
     * @param usuario Usuario propietario de las listas.
     * @return Lista de listas del usuario.
     */
    List<Lista> findByUsuario(Usuario usuario);

    /**
     * Busca una lista específica por su ID y el usuario propietario.
     * @param id UUID de la lista.
     * @param usuario Usuario propietario.
     * @return Lista si existe y pertenece al usuario, o vacío si no.
     */
    Optional<Lista> findByIdAndUsuario(UUID id, Usuario usuario);

    /**
     * Verifica si una lista con un nombre concreto existe para un usuario.
     * @param nombre Nombre de la lista.
     * @param usuario Usuario propietario.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByNombreAndUsuario(String nombre, Usuario usuario);

    /**
     * Elimina una lista por su ID y el usuario propietario (opcional para seguridad adicional).
     * @param id UUID de la lista.
     * @param usuario Usuario propietario.
     */
    void deleteByIdAndUsuario(UUID id, Usuario usuario);
}
