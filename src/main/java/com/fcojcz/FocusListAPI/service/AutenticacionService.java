package com.fcojcz.FocusListAPI.service;

import com.fcojcz.FocusListAPI.model.dto.autenticacion.*;
import com.fcojcz.FocusListAPI.repository.PasswordResetTokenRepository;
import com.fcojcz.FocusListAPI.repository.UsuarioRepository;
import com.fcojcz.FocusListAPI.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AutenticacionService {

    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private PasswordResetTokenRepository tokenRepo;
    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private JwtUtils jwt;

    private static final Logger logger = LoggerFactory.getLogger(AutenticacionService.class);

    public JwtResponseDTO login(LoginRequestDTO loginRequest) {
        try {
            logger.info("Construyendo token");
            String userOrEmail;
            if (loginRequest.getUsername() == null || loginRequest.getUsername().isBlank()) {
                userOrEmail = loginRequest.getEmail();
            } else {
                userOrEmail = loginRequest.getUsername();
            }

            Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    userOrEmail,
                    loginRequest.getPassword()
                )
            );
            logger.info("Generando token");
            String token = jwt.generateJwtToken(auth);
            return new JwtResponseDTO(token, "Bearer"); //NOTA: Hay que añadir el tipo de autenticación
        } catch (AuthenticationException e) {
            logger.error("Error al autenticar el usuario: {}", e.getMessage());
            throw new RuntimeException("Error al autenticar el usuario: " + e.getMessage());
        }
    }
}