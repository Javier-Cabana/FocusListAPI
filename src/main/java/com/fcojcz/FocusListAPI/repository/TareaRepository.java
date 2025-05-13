package com.fcojcz.FocusListAPI.repository;

import com.fcojcz.FocusListAPI.model.entity.Tarea;
import com.fcojcz.FocusListAPI.model.entity.Lista;
import com.fcojcz.FocusListAPI.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, UUID> {

    /**
     * Busca una tarea por su ID y lista asociada, útil para validación y seguridad.
     * @param id UUID de la tarea.
     * @param lista Lista a la que debe pertenecer la tarea.
     * @return Tarea si existe y pertenece a la lista, o vacío.
     */
    Optional<Tarea> findByIdAndLista(UUID id, Lista lista);

    /**
    * Devuelve todas las tareas contenidas en una lista.
    * @param lista Lista propietaria de las tareas.
    * @param pageable Información de paginación
    * @return Página con todas las tareas de la lista.
    */
    Page<Tarea> findAllByLista(Pageable pageable, Lista lista);

    /**
     * Verifica si existe una tarea con un título específico dentro de una lista.
     * @param titulo Título de la tarea.
     * @param lista Lista en la que se busca la tarea.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByTituloAndLista(String titulo, Lista lista);
}
