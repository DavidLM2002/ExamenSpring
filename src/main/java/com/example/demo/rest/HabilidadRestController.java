package com.example.demo.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Habilidad;
import com.example.demo.service.HabilidadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/habilidades", produces = "application/json")
public class HabilidadRestController {

    @Autowired
    private HabilidadService habilidadService;

    // LISTAR TODOS
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Habilidad> listar() {
        return habilidadService.listarTodos();
    }

    // OBTENER UNO POR ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Habilidad obtener(@PathVariable Long id) {
        return habilidadService.buscarPorId(id);
    }

    // CREAR NUEVO
    @PostMapping
    public Habilidad crear(@Valid @RequestBody Habilidad habilidad) {
        return habilidadService.guardar(habilidad);
    }

    // ACTUALIZAR EXISTENTE
    @PutMapping("/{id}")
    public Habilidad actualizar(@PathVariable Long id, @RequestBody Habilidad habilidad) {
        habilidad.setId(id);
        return habilidadService.guardar(habilidad);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        habilidadService.eliminar(id);
    }
}

