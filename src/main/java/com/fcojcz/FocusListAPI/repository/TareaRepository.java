package com.fcojcz.FocusListAPI.repository;

import com.fcojcz.FocusListAPI.model.entity.Tarea;
import com.fcojcz.FocusListAPI.model.entity.Lista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TareaRepository extends JpaRepository<Tarea, UUID> {

    /**
     * Devuelve todas las tareas de una lista específica.
     * @param lista Lista a la que pertenecen las tareas.
     * @return Lista de tareas asociadas a esa lista.
     */
    List<Tarea> findByLista(Lista lista);

    /**
     * Devuelve todas las tareas completadas o no completadas de una lista específica.
     * @param lista Lista asociada.
     * @param completada Estado de la tarea (true o false).
     * @return Lista de tareas según su estado de completado.
     */
    List<Tarea> findByListaAndCompletada(Lista lista, boolean completada);

    /**
     * Busca una tarea por su ID y lista asociada, útil para validación y seguridad.
     * @param id UUID de la tarea.
     * @param lista Lista a la que debe pertenecer la tarea.
     * @return Tarea si existe y pertenece a la lista, o vacío.
     */
    Optional<Tarea> findByIdAndLista(UUID id, Lista lista);

    /**
     * Verifica si existe una tarea con un título específico dentro de una lista.
     * @param titulo Título de la tarea.
     * @param lista Lista en la que se busca la tarea.
     * @return true si existe, false en caso contrario.
     */
    boolean existsByTituloAndLista(String titulo, Lista lista);

    /**
     * Elimina una tarea por su ID y su lista asociada (opcional para mayor seguridad).
     * @param id UUID de la tarea.
     * @param lista Lista a la que pertenece la tarea.
     */
    void deleteByIdAndLista(UUID id, Lista lista);
}
