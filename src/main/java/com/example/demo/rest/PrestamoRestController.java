package com.example.demo.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Prestamo;
import com.example.demo.service.PrestamoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/prestamos", produces = "application/json")
public class PrestamoRestController {

    @Autowired
    private PrestamoService prestamoService;

    // LISTAR TODOS
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Prestamo> listar() {
        System.out.println(">>> ENTRANDO EN /api/prestamos (LISTAR)");
        return prestamoService.listarTodos();
    }

    // OBTENER UNO POR ID
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Prestamo obtener(@PathVariable Long id) {
        System.out.println(">>> BUSCANDO PRESTAMO ID: " + id);
        return prestamoService.buscarPorId(id);
    }

    // CREAR NUEVO
    @PostMapping
    public Prestamo crear(@Valid @RequestBody Prestamo prestamo) {
        System.out.println(">>> CREANDO NUEVO PRESTAMO");
        return prestamoService.guardar(prestamo);
    }

    // ACTUALIZAR EXISTENTE
    @PutMapping("/{id}")
    public Prestamo actualizar(@PathVariable Long id, @RequestBody Prestamo prestamo) {
        System.out.println(">>> ACTUALIZANDO PRESTAMO ID: " + id);
        // Seteamos el ID de la URL al objeto para asegurar que JPA haga un UPDATE y no un INSERT
        prestamo.setId(id);
        return prestamoService.guardar(prestamo);
    }

    // ELIMINAR
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        System.out.println(">>> ELIMINANDO PRESTAMO ID: " + id);
        prestamoService.eliminar(id);
    }
}

