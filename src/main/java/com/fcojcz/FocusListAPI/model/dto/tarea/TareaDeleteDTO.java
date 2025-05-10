package com.fcojcz.FocusListAPI.model.dto.tarea;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TareaDeleteDTO {

    @NotNull(message = "El id de la tarea es obligatorio")
    private UUID id;
}
