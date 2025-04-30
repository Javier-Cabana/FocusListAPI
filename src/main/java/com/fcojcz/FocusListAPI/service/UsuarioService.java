package com.fcojcz.FocusListAPI.service;

import com.fcojcz.FocusListAPI.model.entity.Usuario;
import com.fcojcz.FocusListAPI.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;

    private static final Logger logger = LoggerFactory.getLogger(UsuarioService.class);

    /**
     * Cargar un usuario por su email
     * @param email Email del usuario a cargar
     * @return Devuelve el usuario si lo encuentra, sino devuelve null
     * @throws RuntimeException Si ocurre un error.
     */
    public Usuario loadUsuarioByEmail(String email) {
        try {
            Usuario result = usuarioRepository.findByEmail(email).orElse(null);

            if (result == null) {
                logger.error("No se ha encontrado el usuario con email: {}", email);
            } else {
                logger.info("Buscando el usuario con email: {}", email);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar el usuario con email: {}", email, e.getMessage());
            throw new RuntimeException("Error al buscar el usuario por email:" + e.getMessage());
        }
    }

    /**
     * Cargar un usuario por su nombre de usuario
     * @param username username del usuario a cargar
     * @return Devuelve el usuario si lo encuentra, sino devuelve null
     * @throws RuntimeException Si ocurre un error.
     */
    public Usuario loadUsuarioByUsername(String username) {
        try {
            Usuario result = usuarioRepository.findByUsername(username).orElse(null);

            if (result == null) {
                logger.error("No se ha encontrado el usuario con username: {}", username);
            } else {
                logger.info("Buscando el usuario con username: {}", username);
            }

            return result;
        } catch (Exception e) {
            logger.error("Error al buscar el usuario con username: {}", username, e.getMessage());
            throw new RuntimeException("Error al buscar el usuario por username:" + e.getMessage());
        }
    }

    /**
     * Verificar si el email ya existe
     * @param email email a verificar
     * @return Devuelve true si lo encuentra, sino devuelve false
     * @throws RuntimeException Si ocurre un error durante la verificación.
     */
    public boolean existsByEmail(String email) {
        try {
            logger.info("Buscando el usuario con email: {}", email);
            return usuarioRepository.existsByEmail(email);
        } catch (Exception e) {
            logger.error("Error al buscar el usuario con email: {}", email, e.getMessage());
            throw new RuntimeException("Error al buscar el usuario por email:" + e.getMessage());
        }
    }

    /**
     *  Devuelve todos los usuarios
     * @param pageable Información de paginación
     * @return Devuelve una página con todos los usuarios
     * @throws RuntimeException Si ocurre un error.
     */
    public Page<Usuario> findAll(Pageable pageable) {
        try {
            logger.info("Buscando todos los usuarios");
            return usuarioRepository.findAll(
                    PageRequest.of(
                            pageable.getPageNumber() > 0
                                    ? pageable.getPageNumber()
                                    : 0,
                            pageable.getPageSize() > 0
                                    ? pageable.getPageSize()
                                    : 10,
                            pageable.getSort()
                    )
            );
        } catch (Exception e) {
            logger.error("Error al buscar todos los usuarios\n" + e.getMessage());
            throw new RuntimeException("Error al buscar todos los usuarios\n" + e.getMessage());
        }
    }

//    public Usuario save(Usuario usuario) {
//
//    }
//
//    public Usuario update(Usuario usuario) {
//
//    public boolean delete(Usuario usuario) {
//
//    }
//
//    public UserResponseDTO mapToUserResponseDTO(Usuario usuario) {
//
//    }
}
