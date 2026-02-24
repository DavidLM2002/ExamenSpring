package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.AlumnoDTO;
import com.example.demo.dto.PrestamoDTO;
import com.example.demo.service.AlumnoService;
import com.example.demo.service.PrestamoService;

import tools.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/informes")
public class InformeController {

    @Autowired
    private AlumnoService alumnoService;

    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping
    public String dashboard(Model model) throws Exception {

        // Mapeamos las entidades Alumno a DTOs limpios para el gráfico
        List<AlumnoDTO> alumnos = alumnoService.listarTodos()
                .stream()
                .map(a -> new AlumnoDTO(
                        a.getId(),
                        a.getNombre(),
                        a.getCurso(),
                        a.getEmail()
                ))
                .collect(Collectors.toList());

        // Mapeamos las entidades Prestamo a DTOs (incluyendo fecha y estado)
        List<PrestamoDTO> prestamos = prestamoService.listarTodos()
                .stream()
                .map(p -> new PrestamoDTO(
                        p.getId(),
                        p.getTituloLibro(),
                        p.getFechaPrestamo(), // Asegúrate de que el DTO maneje bien la fecha
                        p.isDevuelto(),
                        p.getAlumno().getNombre() // Para el gráfico de Top Alumnos
                ))
                .collect(Collectors.toList());

        // Pasamos los JSON al modelo
        model.addAttribute("alumnosJson", mapper.writeValueAsString(alumnos));
        model.addAttribute("prestamosJson", mapper.writeValueAsString(prestamos));

        return "informes/dashboard";
    }
}


