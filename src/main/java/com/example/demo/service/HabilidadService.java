package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.Naufrago;
import com.example.demo.entity.Habilidad;
import com.example.demo.repository.HabilidadRepository;

import jakarta.validation.Valid;

@Service
public class HabilidadService {

    @Autowired
    private HabilidadRepository habilidadRepository;

    public List<Habilidad> listarTodos() {
        return habilidadRepository.findAll();
    }

    public Habilidad guardar(Habilidad p) {
        return habilidadRepository.save(p);
    }


    public void eliminar(Long id) {
        habilidadRepository.deleteById(id);
    }
    
    public Habilidad buscarPorId(Long id) {
        return habilidadRepository.findById(id).orElse(null);
    }
}

