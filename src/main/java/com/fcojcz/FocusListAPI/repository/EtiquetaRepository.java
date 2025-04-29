package com.fcojcz.FocusListAPI.repository;

import com.fcojcz.FocusListAPI.model.entity.Etiqueta;
import com.fcojcz.FocusListAPI.model.enums.PrioridadEtiqueta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EtiquetaRepository extends JpaRepository<Etiqueta, UUID> {
    boolean existsByPrioridad(PrioridadEtiqueta prioridad);

    Optional<Etiqueta> findByPrioridad(PrioridadEtiqueta prioridad);
}
