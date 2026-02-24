package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Alumno;
import com.example.demo.repository.AlumnoRepository;
import com.example.demo.service.AlumnoService;

import jakarta.validation.Valid;

@Controller // Indica que es un controlador web que devuelve vistas (HTML)
@RequestMapping("/alumnos") // Todas las rutas de este archivo empiezan por /alumnos
public class AlumnoController {

    @Autowired // Inyecta el repositorio para acceder a la base de datos
    private AlumnoRepository alumnoRepository;

    // LISTAR: Muestra la tabla con todos los alumnos
    @GetMapping
    public String listAlumnos(Model model) {
        // "alumnos" es el nombre que usaremos en el HTML para el bucle th:each
        model.addAttribute("alumnos", alumnoRepository.findAll());
        return "alumnos/lista"; // Busca el archivo templates/alumnos/lista.html
    }

    // NUEVO: Muestra el formulario vacío para crear un alumno
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        // Pasamos un objeto Alumno vacío para que th:field sepa dónde escribir
        model.addAttribute("alumno", new Alumno());
        return "alumnos/form";
    }

    // GUARDAR: Procesa los datos enviados por el formulario (Add y Edit)
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Alumno alumno, BindingResult result) {
        // Si las validaciones (@NotBlank, @Size, @Email) fallan, volvemos al formulario
        if (result.hasErrors()) {
            return "alumnos/form";
        }
        // Si el objeto ya tiene un ID, Hibernate hará un Update; si no, un Insert
        alumnoRepository.save(alumno);
        return "redirect:/alumnos"; // Tras guardar, volvemos a la lista
    }

    // EDITAR: Busca un alumno por ID y lo carga en el formulario
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        // Buscamos el alumno. Si no existe, lanzamos una excepción
        Alumno alumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("alumno", alumno);
        return "alumnos/form";
    }

    // ELIMINAR: Borra el registro por su ID
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        alumnoRepository.deleteById(id);
        return "redirect:/alumnos";
    }
}


