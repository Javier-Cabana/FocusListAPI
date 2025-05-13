package com.fcojcz.FocusListAPI.controller;

import com.fcojcz.FocusListAPI.model.dto.tarea.*;
import com.fcojcz.FocusListAPI.model.entity.Tarea;
import com.fcojcz.FocusListAPI.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tarea")
public class TareaController {
    @Autowired
    private TareaService tareaService;

    @PostMapping()
    public ResponseEntity<TareaResponseDTO> createTarea(@RequestBody TareaCreateDTO tareaCreateDTO) {
        Tarea tarea = tareaService.save(tareaCreateDTO);

        if (tarea == null) return ResponseEntity.badRequest().build();

        TareaResponseDTO response = tareaService.mapToTareaResponseDTO(tarea);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<TareaResponseDTO>> getAllTareas(Pageable pageable, @RequestBody TareaGetDTO tareaGetDTO) {
        Page<Tarea> tareas = tareaService.loadAllByLista(pageable, tareaGetDTO.getIdLista());

        if (tareas == null) return ResponseEntity.badRequest().build();

        Page<TareaResponseDTO> response = tareas.map(tarea -> tareaService.mapToTareaResponseDTO(tarea));
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<TareaResponseDTO> getTarea(@RequestBody TareaGetDTO tareaGetDTO) {
        Tarea tarea = tareaService.loadById(tareaGetDTO.getId());

        if (tarea == null) return ResponseEntity.badRequest().build();

        TareaResponseDTO response = tareaService.mapToTareaResponseDTO(tarea);
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<TareaResponseDTO> updateTarea(@RequestBody TareaUpdateDTO tareaUpdateDTO) {
        Tarea tarea = tareaService.update(tareaUpdateDTO);

        if (tarea == null) return ResponseEntity.badRequest().build();

        TareaResponseDTO response = tareaService.mapToTareaResponseDTO(tarea);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteTarea(@RequestBody TareaDeleteDTO tareaDeleteDTO) {
        boolean borrado = tareaService.delete(tareaDeleteDTO);

        if (borrado){
            return ResponseEntity.ok("Tarea borrada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado la tarea");
        }
    }
}
