package com.example.demo.unit;

import com.example.demo.entity.Prestamo;
import com.example.demo.repository.PrestamoRepository;
import com.example.demo.service.PrestamoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Unitario {

    @Mock
    private PrestamoRepository prestamoRepository;

    @InjectMocks
    private PrestamoService prestamoService;
    
    @Test
    void UT1_devueltoTrue_exigeFechaDevolucion() {
        Prestamo p = new Prestamo();
        p.setTituloLibro("Test");
        p.setFechaPrestamo(LocalDate.now());
        p.setDevuelto(true);
        p.setFechaDevolucion(null); // inválido

        boolean valido = p.isValidacionDevolucion();

        assertFalse(valido, "Debe ser inválido si devuelto=true y fechaDevolucion=null");
    }
    
    @Test
    void UT2_fechaDevolucionDebeSerPosteriorOIgual() {
        Prestamo p = new Prestamo();
        p.setTituloLibro("Test");
        p.setFechaPrestamo(LocalDate.of(2024, 1, 10));
        p.setDevuelto(true);
        p.setFechaDevolucion(LocalDate.of(2024, 1, 5)); // anterior → inválido

        boolean valido = p.isValidacionDevolucion();

        assertFalse(valido, "La fecha de devolución no puede ser anterior a la fecha de préstamo");
    }
}

