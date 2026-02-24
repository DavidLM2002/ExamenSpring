package com.example.demo.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Alumno;
import com.example.demo.service.AlumnoService;

@RestController
@RequestMapping(value = "/api/alumnos", produces = "application/json")
public class AlumnoRestController {

    @Autowired
    private AlumnoService alumnoService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Alumno> listar() {
        System.out.println(">>> ENTRANDO EN /api/alumnos (LISTAR)");
        return alumnoService.listarTodos();
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Alumno obtener(@PathVariable Long id) {
        System.out.println(">>> BUSCANDO ALUMNO ID: " + id);
        return alumnoService.buscarPorId(id);
    }

    @PostMapping
    public Alumno crear(@RequestBody Alumno alumno) {
        System.out.println(">>> CREANDO NUEVO ALUMNO");
        return alumnoService.guardar(alumno);
    }

    // MÉTODO UPDATE (Actualizar)
    @PutMapping("/{id}")
    public Alumno actualizar(@PathVariable Long id, @RequestBody Alumno alumno) {
        System.out.println(">>> ACTUALIZANDO ALUMNO ID: " + id);
        // Nos aseguramos de que el ID del objeto sea el de la URL
        alumno.setId(id); 
        return alumnoService.guardar(alumno);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        System.out.println(">>> ELIMINANDO ALUMNO ID: " + id);
        alumnoService.eliminar(id);
    }
}

