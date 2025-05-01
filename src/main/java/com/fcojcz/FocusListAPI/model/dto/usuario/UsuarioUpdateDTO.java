package com.fcojcz.FocusListAPI.model.dto.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioUpdateDTO {

    @NotNull(message = "El id es obligatorio")
    private UUID id;

    @NotBlank(message = "El username no puede estar vacío")
    private String username;

    @Email(message = "El email debe ser valido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
}
