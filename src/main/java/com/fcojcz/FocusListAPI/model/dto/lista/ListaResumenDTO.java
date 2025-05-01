package com.fcojcz.FocusListAPI.model.dto.lista;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ListaResumenDTO {
    private UUID id;
    private String nombre;
}
