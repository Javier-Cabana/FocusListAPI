package com.fcojcz.FocusListAPI.controller;

import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioCreateDTO;
import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioDeleteDTO;
import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioResponseDTO;
import com.fcojcz.FocusListAPI.model.entity.Usuario;
import com.fcojcz.FocusListAPI.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/all")
    public ResponseEntity<Page<UsuarioResponseDTO>> getAllUsers(@PageableDefault() Pageable pageable) {
        Page<Usuario> result = usuarioService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<UsuarioResponseDTO> response = result.map(usuario -> {
            return UsuarioResponseDTO.builder()
                    .id(usuario.getId())
                    .username(usuario.getUsername())
                    .email(usuario.getEmail())
                    .password(usuario.getPassword())
                    .build();
        });

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UsuarioResponseDTO> getUserByUsername(@PathVariable("username") String username) {
        Usuario usuario = usuarioService.loadUsuarioByUsername(username);

        if (usuario == null) return ResponseEntity.badRequest().build();

        UsuarioResponseDTO usuarioResponseDTO = usuarioService.mapToUserResponseDTO(usuario);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<UsuarioResponseDTO> createUsuario(@RequestBody UsuarioCreateDTO dto) {
        Usuario usuario = usuarioService.save(dto);

        if (usuario == null) return ResponseEntity.badRequest().build();

        UsuarioResponseDTO response = usuarioService.mapToUserResponseDTO(usuario);
        return ResponseEntity.ok(response);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<UsuarioResponseDTO> updateUsuario() {
//
//    }
//
    @DeleteMapping()
    public ResponseEntity<String> deleteUsuario(@RequestBody UsuarioDeleteDTO dto) {
        boolean borrado = usuarioService.delete(dto);

        if(borrado){
            return ResponseEntity.ok("Usuario borrado correctamente");
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
