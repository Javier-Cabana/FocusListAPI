package com.fcojcz.FocusListAPI.model.dto.lista;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListaGetDTO {
    @NotBlank(message = "El nombre de la lista no puede estar vacío")
    private String nombre;

    @NotNull(message = "Debe indicarse el usuario propietario")
    private UUID idUsuario;
}
