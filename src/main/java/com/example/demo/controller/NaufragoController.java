package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Naufrago;
import com.example.demo.repository.NaufragoRepository;
import com.example.demo.service.NaufragoService;

import jakarta.validation.Valid;

@Controller // Indica que es un controlador web que devuelve vistas (HTML)
@RequestMapping("/naufragos") // Todas las rutas de este archivo empiezan por /alumnos
public class NaufragoController {

    @Autowired // Inyecta el repositorio para acceder a la base de datos
    private NaufragoRepository naufragoRepository;

    @GetMapping
    public String listNaufragos(Model model) {
        model.addAttribute("naufragos", naufragoRepository.findAllWithHabilidads());
        return "naufragos/lista"; 
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("naufrago", new Naufrago());
        return "naufragos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Naufrago naufrago, BindingResult result) {
        if (result.hasErrors()) {
            return "naufragos/form";
        }
        naufragoRepository.save(naufrago);
        return "redirect:/naufragos";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Naufrago naufrago = naufragoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        model.addAttribute("naufrago", naufrago);
        return "naufragos/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        naufragoRepository.deleteById(id);
        return "redirect:/naufragos";
    }
}