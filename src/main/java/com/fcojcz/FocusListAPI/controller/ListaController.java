package com.fcojcz.FocusListAPI.controller;

import com.fcojcz.FocusListAPI.model.dto.lista.ListaCreateDTO;
import com.fcojcz.FocusListAPI.model.dto.lista.ListaDeleteDTO;
import com.fcojcz.FocusListAPI.model.dto.lista.ListaResponseDTO;
import com.fcojcz.FocusListAPI.model.dto.lista.ListaUpdateDTO;
import com.fcojcz.FocusListAPI.model.entity.Lista;
import com.fcojcz.FocusListAPI.service.ListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/listas")
public class ListaController {
    @Autowired
    private ListaService listaService;

    /**
     * Obtener todas las listas del usuario
     * Obtener una lista por su nombre
     * Crear una lista
     * Actualizar una lista
     * Borrar una lista
    */

    @GetMapping("/all/{username}")
    public ResponseEntity<Page<ListaResponseDTO>> getAllListas(@PageableDefault() Pageable pageable, @PathVariable("username") String username) {
        Page<Lista> result = listaService.findAllByUsuario(pageable, username);

        if (result == null) return ResponseEntity.badRequest().build();

        Page<ListaResponseDTO> response = result.map(lista -> listaService.mapToListaResponseDTO(lista));

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{name}")
    public ResponseEntity<ListaResponseDTO> getListaByName(@PathVariable("name") String name) {
        Lista lista = listaService.loadByName(name);

        if (lista == null) return ResponseEntity.badRequest().build();

        ListaResponseDTO listaResponseDTO = listaService.mapToListaResponseDTO(lista);

        return ResponseEntity.ok(listaResponseDTO);
    }

    @PostMapping()
    public ResponseEntity<ListaResponseDTO> createLista(@RequestBody ListaCreateDTO listaCreateDTO) {
        Lista lista = listaService.save(listaCreateDTO);

        if (lista == null) return ResponseEntity.badRequest().build();

        ListaResponseDTO response = listaService.mapToListaResponseDTO(lista);
        return ResponseEntity.ok(response);
    }

    @PutMapping()
    public ResponseEntity<ListaResponseDTO> updateLista(@RequestBody ListaUpdateDTO listaUpdateDTO) {
        Lista lista = listaService.update(listaUpdateDTO);

        if (lista == null) return ResponseEntity.badRequest().build();

        ListaResponseDTO response = listaService.mapToListaResponseDTO(lista);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteLista(@RequestBody ListaDeleteDTO listaDeleteDTO) {
        boolean borrado = listaService.delete(listaDeleteDTO);

        if (borrado){
            return ResponseEntity.ok("Lista borrada correctamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se ha encontrado la lista");
        }
    }

}
