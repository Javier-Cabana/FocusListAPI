package com.fcojcz.FocusListAPI.service;

import com.fcojcz.FocusListAPI.model.dto.lista.ListaResumenDTO;
import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioCreateDTO;
import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioDeleteDTO;
import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioResponseDTO;
import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioUpdateDTO;
import com.fcojcz.FocusListAPI.model.entity.Usuario;
import com.fcojcz.FocusListAPI.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

    /**
     * Metodo para guardar un usuario
     * @param usuarioCreateDTO DTO del usuario que queremos guardar en la base de datos
     * @return Usuario guardado
     * @throws RuntimeException Si el email ya esta en uso u ocurre un error durante el guardado.
     */
    public Usuario save(UsuarioCreateDTO usuarioCreateDTO) {
        try {
            if (existsByEmail(usuarioCreateDTO.getEmail())) {
                logger.error("Ya existe un usuario con el email: {}", usuarioCreateDTO.getEmail());
                throw new RuntimeException("Ya existe un usuario con el email: " + usuarioCreateDTO.getEmail());
            }
            logger.info("Creando el usuario a partir del DTO: {}", usuarioCreateDTO);
            Usuario usuario = Usuario.builder()
                    .username(usuarioCreateDTO.getUsername())
                    .email(usuarioCreateDTO.getEmail())
                    .password(usuarioCreateDTO.getPassword())
                    .build();
            logger.info("Guardando el usuario con username: {}", usuario);
            return usuarioRepository.save(usuario);
        } catch (Exception e) {
            logger.error("Error al guardar el usuario: {}", e.getMessage());
            throw new RuntimeException("Error al guardar el usuario\n" + e.getMessage());
        }
    }

    /**
     * Metodo para actualizar un usuario
     * @param usuarioUpdateDTO DTO del usuario que queremos actualizar en la base de datos
     * @return Usuario actualizado
     * @throws RuntimeException Si ocurre un error durante la actualización.
     */
    public Usuario update(UsuarioUpdateDTO usuarioUpdateDTO) {
        try {
            logger.info("Buscando el usuario a actualizar con ID: '{}'", usuarioUpdateDTO.getId());
            Usuario usuarioToUpdate = usuarioRepository.findById(usuarioUpdateDTO.getId()).get();

            if (usuarioToUpdate != null) {
                usuarioToUpdate.setUsername(usuarioUpdateDTO.getUsername());
                usuarioToUpdate.setEmail(usuarioUpdateDTO.getEmail());
                usuarioToUpdate.setPassword(usuarioUpdateDTO.getPassword());
            }

            logger.info("Actualizando el usuario: {}", usuarioToUpdate);
            return usuarioRepository.save(usuarioToUpdate);
        } catch (Exception e) {
            logger.error("Error al actualizar el usuario: {}", e.getMessage());
            throw new RuntimeException("Error al actualizar el usuario\n" + e.getMessage());
        }
    }

    public boolean delete(UsuarioDeleteDTO usuarioDeleteDTO) {
        try {
            Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioDeleteDTO.getId());
            if (usuarioOptional.isPresent()) {
                logger.info("Borrando el usuario con id: {}", usuarioDeleteDTO.getId());
                usuarioRepository.deleteById(usuarioDeleteDTO.getId());
                return true;
            }
            logger.error("No se ha encontrado el usuario con id: {}", usuarioDeleteDTO.getId());
            return false;
        } catch (Exception e) {
            logger.error("Error al borrar el usuario con id: {}", usuarioDeleteDTO.getId(), e.getMessage());
            throw new RuntimeException("Error al borrar el usuario.\n" + e.getMessage());
        }
    }

    /**
     * Metodo para mapear un usuario a un UsuarioResponseDTO
     * @param usuario Objeto a mapear
     * @return Devuelve el UsuarioResponseDTO mapeado
     * @throws RuntimeException Si ocurre un error durante el mapeo.
     */
    public UsuarioResponseDTO mapToUserResponseDTO(Usuario usuario) {
        try {
            logger.info("Mapeando el usuario con id: {}", usuario.getId());

            Set<ListaResumenDTO> listas = new HashSet<>();
            //TODO Mapear las listas cuando tengamos el repository y servicio de listas

            return UsuarioResponseDTO.builder()
                    .id(usuario.getId())
                    .username(usuario.getUsername())
                    .email(usuario.getEmail())
                    .password(usuario.getPassword())
                    .listas(listas)
                    .build();
        } catch (Exception e) {
            logger.error("Error al mapear el usuario\n" + e.getMessage());
            throw new RuntimeException("Error al mapear el usuario\n" + e.getMessage());
        }
    }
}