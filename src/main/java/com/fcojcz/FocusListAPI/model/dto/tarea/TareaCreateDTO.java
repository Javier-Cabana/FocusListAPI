package com.fcojcz.FocusListAPI.model.dto.tarea;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TareaCreateDTO {

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcion;

    private LocalDateTime fechaVencimiento;

    @NotNull(message = "Debe asignarse a una lista")
    private UUID idLista;

    private UUID idEtiqueta; // opcional
}
