package com.fcojcz.FocusListAPI.model.dto.lista;

import com.fcojcz.FocusListAPI.model.dto.tarea.TareaResponseDTO;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListaResponseDTO {

    private UUID id;

    private String nombre;

    private LocalDate fechaCreacion;

    private UUID idUsuario;

    private Set<TareaResponseDTO> tareas;
}
