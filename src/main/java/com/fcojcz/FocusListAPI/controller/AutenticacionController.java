package com.fcojcz.FocusListAPI.controller;

import com.fcojcz.FocusListAPI.model.dto.autenticacion.JwtResponseDTO;
import com.fcojcz.FocusListAPI.model.dto.autenticacion.LoginRequestDTO;
import com.fcojcz.FocusListAPI.service.AutenticacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticacion")
public class AutenticacionController {

    @Autowired
    private AutenticacionService autenticacionService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponseDTO> login(LoginRequestDTO dto) {
        JwtResponseDTO jwt = autenticacionService.login(dto);
        return ResponseEntity.ok(jwt);
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
