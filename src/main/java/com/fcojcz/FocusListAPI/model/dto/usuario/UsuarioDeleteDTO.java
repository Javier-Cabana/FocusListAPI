package com.fcojcz.FocusListAPI.model.dto.usuario;

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
public class UsuarioDeleteDTO {

    @NotNull(message = "El id es obligatorio")
    private UUID id;
}
