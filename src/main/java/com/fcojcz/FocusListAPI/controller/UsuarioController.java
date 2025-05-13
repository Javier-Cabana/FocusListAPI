package com.fcojcz.FocusListAPI.controller;

import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioDeleteDTO;
import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioResponseDTO;
import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioUpdateDTO;
import com.fcojcz.FocusListAPI.model.entity.Usuario;
import com.fcojcz.FocusListAPI.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/all")
    public ResponseEntity<Page<UsuarioResponseDTO>> getAllUsers(@PageableDefault() Pageable pageable) {
        Page<Usuario> result = usuarioService.findAll(pageable);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<UsuarioResponseDTO> response = result.map(usuario -> usuarioService.mapToUserResponseDTO(usuario));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UsuarioResponseDTO> getUserByUsername(@PathVariable("username") String username) {
        Usuario usuario = usuarioService.loadUsuarioByUsername(username);

        if (usuario == null) return ResponseEntity.badRequest().build();

        UsuarioResponseDTO usuarioResponseDTO = usuarioService.mapToUserResponseDTO(usuario);
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @PutMapping()
    public ResponseEntity<UsuarioResponseDTO> updateUsuario(@RequestBody UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario usuario = usuarioService.update(usuarioUpdateDTO);

        if (usuario == null) return ResponseEntity.badRequest().build();

        UsuarioResponseDTO response = usuarioService.mapToUserResponseDTO(usuario);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteUsuario(@RequestBody UsuarioDeleteDTO dto) {
        boolean borrado = usuarioService.delete(dto);

        if(borrado){
            return ResponseEntity.ok("Usuario borrado correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado el usuario");
        }
    }
}
