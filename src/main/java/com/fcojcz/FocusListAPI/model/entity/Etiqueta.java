package com.fcojcz.FocusListAPI.model.entity;

import com.fcojcz.FocusListAPI.model.enums.PrioridadEtiqueta;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "etiquetas")
public class Etiqueta {

    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = "id_etiqueta", updatable = false, nullable = false)
    private UUID id_etiqueta;

    @Enumerated(EnumType.STRING)
    @Column(name = "prioridad", nullable = false)
    private PrioridadEtiqueta prioridad;

    @OneToMany(mappedBy = "etiqueta")
    private List<Tarea> tareas = new ArrayList<>();
}
