package com.fcojcz.FocusListAPI.controller;

import com.fcojcz.FocusListAPI.model.dto.usuario.UsuarioResponseDTO;
import com.fcojcz.FocusListAPI.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

//    @GetMapping("/all")
//    public ResponseEntity<Page<UsuarioResponseDTO>> getAllUsers() {
//
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<UsuarioResponseDTO> getUserById() {
//
//    }
//
//    @PostMapping("/{id}")
//    public ResponseEntity<UsuarioResponseDTO> createUsuario() {
//
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<UsuarioResponseDTO> updateUsuario() {
//
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<UsuarioResponseDTO> deleteUsuario() {
//
//    }
}
