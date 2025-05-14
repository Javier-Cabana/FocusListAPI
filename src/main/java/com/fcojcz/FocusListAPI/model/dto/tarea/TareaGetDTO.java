package com.fcojcz.FocusListAPI.model.dto.tarea;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TareaGetDTO {

    private UUID id;
    private UUID idLista;
}
