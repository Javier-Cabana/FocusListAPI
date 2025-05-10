package com.fcojcz.FocusListAPI.model.dto.tarea;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TareaUpdateDTO {

    @NotNull(message = "El id de la tarea es obligatorio")
    private UUID id;

    @NotBlank(message = "El título no puede estar vacío")
    private String titulo;

    private String descripcion;

    private Boolean completada;

    private LocalDateTime fechaVencimiento;

    @NotNull(message = "Debe indicarse la lista a la que pertenece")
    private UUID idLista;

    private UUID idEtiqueta;
}
