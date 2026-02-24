package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Prestamo;
import com.example.demo.repository.PrestamoRepository;

import jakarta.validation.Valid;

@Service
public class PrestamoService {

    @Autowired
    private PrestamoRepository prestamoRepository;

    public List<Prestamo> listarTodos() {
        return prestamoRepository.findAll();
    }

    public Prestamo guardar(Prestamo p) {
        /*if (p.isDevuelto() && p.getFechaDevolucion() == null) {
            throw new IllegalArgumentException("Error UT1");
        }
        if (p.getFechaDevolucion() != null && p.getFechaDevolucion().isBefore(p.getFechaPrestamo())) {
            throw new IllegalArgumentException("Error UT2");
        }*/
        return prestamoRepository.save(p);
    }

    // Método extra: Lógica para devolver un libro cómodamente
    public Prestamo finalizarPrestamo(Long id) {
        Prestamo p = prestamoRepository.findById(id).orElse(null);
        if (p != null) {
            p.setDevuelto(true);
            p.setFechaDevolucion(LocalDate.now()); // Seteamos fecha actual
            return prestamoRepository.save(p);
        }
        return null;
    }

    public void eliminar(Long id) {
        prestamoRepository.deleteById(id);
    }
    
    public Prestamo buscarPorId(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }
}

