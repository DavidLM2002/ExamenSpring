package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Habilidad;
import com.example.demo.entity.Naufrago;
import com.example.demo.repository.NaufragoRepository;
import com.example.demo.repository.HabilidadRepository;
import com.example.demo.service.NaufragoService;
import com.example.demo.service.HabilidadService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/habilidades")
public class HabilidadController {

    @Autowired
    private HabilidadRepository habilidadRepository;

    @Autowired
    private NaufragoRepository naufragoRepository; 

    @GetMapping
    public String listPrestamos(Model model) {
        model.addAttribute("habilidades", habilidadRepository.findAll());
        return "habilidades/lista";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("habilidad", new Habilidad());
        model.addAttribute("naufragos", naufragoRepository.findAll());
        return "habilidades/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Habilidad habilidad, BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("naufragos", naufragoRepository.findAll());
            return "habilidades/form";
        }
        // Si el objeto habilidad trae un naufrago con id, lo resolvemos desde BD antes de guardar
        if (habilidad.getNaufrago() != null && habilidad.getNaufrago().getId() != null) {
            Long naufragoId = habilidad.getNaufrago().getId();
            Naufrago nau = naufragoRepository.findById(naufragoId).orElse(null);
            habilidad.setNaufrago(nau);
        }
        habilidadRepository.save(habilidad);
        return "redirect:/habilidades";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Habilidad habilidad = habilidadRepository.findById(id).orElseThrow();
        model.addAttribute("habilidad", habilidad);
        model.addAttribute("naufragos", naufragoRepository.findAll());
        return "habilidades/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        habilidadRepository.deleteById(id);
        return "redirect:/habilidades";
    }
}