package com.fcojcz.FocusListAPI.service;

import com.fcojcz.FocusListAPI.model.entity.Etiqueta;
import com.fcojcz.FocusListAPI.model.enums.PrioridadEtiqueta;
import com.fcojcz.FocusListAPI.repository.EtiquetaRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class EtiquetaInitializer {

    private final EtiquetaRepository etiquetaRepository;

    @PostConstruct
    public void initEtiquetas() {
        Arrays.stream(PrioridadEtiqueta.values()).forEach(prioridad -> {
            boolean exists = etiquetaRepository.existsByPrioridad(prioridad);
            if (!exists) {
                etiquetaRepository.save(Etiqueta.builder()
                        .prioridad(prioridad)
                        .build());
            }
        });
    }
}
