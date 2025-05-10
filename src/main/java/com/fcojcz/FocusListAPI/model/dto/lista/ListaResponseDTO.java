package com.fcojcz.FocusListAPI.model.dto.lista;

import com.fcojcz.FocusListAPI.model.dto.tarea.TareaResponseDTO;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListaResponseDTO {

    private UUID id;

    private String nombre;

    private Date fechaCreacion;

    private UUID idUsuario;

    private List<TareaResponseDTO> tareas;
}
