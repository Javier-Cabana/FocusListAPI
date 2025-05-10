package com.fcojcz.FocusListAPI.model.dto.lista;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListaCreateDTO {

    @NotBlank(message = "El nombre de la lista es obligatorio")
    private String nombre;

    @NotNull(message = "Debe especificarse el usuario al que pertenece la lista")
    private UUID idUsuario;
}
