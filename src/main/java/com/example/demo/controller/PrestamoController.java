package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Prestamo;
import com.example.demo.repository.AlumnoRepository;
import com.example.demo.repository.PrestamoRepository;
import com.example.demo.service.AlumnoService;
import com.example.demo.service.PrestamoService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private AlumnoRepository alumnoRepository; // Necesario para elegir alumnos en el dropdown

    // LISTAR: Muestra todos los préstamos realizados
    @GetMapping
    public String listPrestamos(Model model) {
        model.addAttribute("prestamos", prestamoRepository.findAll());
        return "prestamos/lista";
    }

    // NUEVO: Formulario de préstamo con el selector de alumnos
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("prestamo", new Prestamo());
        // Enviamos la lista completa de alumnos para rellenar el <select>
        model.addAttribute("alumnos", alumnoRepository.findAll());
        return "prestamos/form";
    }

    // GUARDAR: Procesa el préstamo y valida la regla de negocio de las fechas
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Prestamo prestamo, BindingResult result, Model model) {
        // Si hay errores de validación (incluyendo el @AssertTrue de la fecha)
        if (result.hasErrors()) {
            // Debemos reenviar la lista de alumnos, o el selector se quedará vacío al recargar
            model.addAttribute("alumnos", alumnoRepository.findAll());
            return "prestamos/form";
        }
        prestamoRepository.save(prestamo);
        return "redirect:/prestamos";
    }

    // EDITAR: Carga un préstamo existente para modificarlo
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Prestamo prestamo = prestamoRepository.findById(id).orElseThrow();
        model.addAttribute("prestamo", prestamo);
        // También cargamos alumnos por si se quiere cambiar el titular del préstamo
        model.addAttribute("alumnos", alumnoRepository.findAll());
        return "prestamos/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        prestamoRepository.deleteById(id);
        return "redirect:/prestamos";
    }
}