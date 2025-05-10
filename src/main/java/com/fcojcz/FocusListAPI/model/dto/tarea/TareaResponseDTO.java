package com.fcojcz.FocusListAPI.model.dto.tarea;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TareaResponseDTO {

    private UUID id;

    private String titulo;

    private String descripcion;

    private Boolean completada;

    private LocalDateTime fechaCreacion;

    private LocalDateTime fechaVencimiento;

    private UUID idLista;

    private UUID idEtiqueta;
}
