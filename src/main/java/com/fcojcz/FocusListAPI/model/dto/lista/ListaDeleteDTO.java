package com.fcojcz.FocusListAPI.model.dto.lista;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListaDeleteDTO {

    @NotNull(message = "El id de la lista es obligatorio")
    private UUID id;
}
