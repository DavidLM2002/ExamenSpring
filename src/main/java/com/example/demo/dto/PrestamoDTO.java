package com.example.demo.dto;

import java.time.LocalDate;

public record PrestamoDTO (
    Long id,
    String tituloLibro,
    LocalDate fechaPrestamo,
    boolean devuelto,
    String nombreAlumno
){}

