package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.NaufragoDTO;
import com.example.demo.service.NaufragoService;
import com.example.demo.service.HabilidadService;

import tools.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/informes")
public class InformeController {

    @Autowired
    private NaufragoService naufragoService;

    @Autowired
    private ObjectMapper mapper;

    @GetMapping
    public String dashboard(Model model) throws Exception {

        // Mapeamos las entidades Alumno a DTOs limpios para el gráfico
        List<NaufragoDTO> naufragos = naufragoService.listarTodos()
                .stream()
                .map(a -> new NaufragoDTO(
                        a.getId(),
                        a.getNombre(),
                        a.getEdad(),
                        a.getSexo(),
                        a.getIsla(),
                        a.getNacionalidad(),
                        a.getFechaRescate()
                ))
                .collect(Collectors.toList());


        // Pasamos los JSON al modelo
        model.addAttribute("naufragosJson", mapper.writeValueAsString(naufragos));

        return "informes/dashboard";
    }
}


