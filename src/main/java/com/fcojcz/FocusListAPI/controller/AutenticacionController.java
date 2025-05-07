package com.fcojcz.FocusListAPI.controller;

import com.fcojcz.FocusListAPI.model.dto.autenticacion.JwtResponseDTO;
import com.fcojcz.FocusListAPI.model.dto.autenticacion.LoginRequestDTO;
import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioCreateDTO;
import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioResponseDTO;
import com.fcojcz.FocusListAPI.model.entity.Usuario;
import com.fcojcz.FocusListAPI.service.AutenticacionService;
import com.fcojcz.FocusListAPI.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticacion")
public class AutenticacionController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AutenticacionService autenticacionService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(LoginRequestDTO dto) {
        JwtResponseDTO jwt = autenticacionService.login(dto);
        return ResponseEntity.ok(jwt);
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> createUsuario(@RequestBody UsuarioCreateDTO dto) {
        Usuario usuario = usuarioService.save(dto);

        if (usuario == null) return ResponseEntity.badRequest().build();

        UsuarioResponseDTO response = usuarioService.mapToUserResponseDTO(usuario);
        return ResponseEntity.ok(response);
    }

//TODO
//    @PostMapping("/forgot-password")
//    public ResponseEntity<?> forgotPassword() {
//
//    }
//
//    @PostMapping("/reset-password")
//    public ResponseEntity<?> resetPassword() {
//
//    }
}
