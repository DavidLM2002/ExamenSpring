package com.example.demo.dto;

import java.time.LocalDate;

public record NaufragoDTO(
    Long id,
    String nombre,
    int edad,
    String sexo,
    String isla,
    String nacionalidad,
    LocalDate fechaRescate
){}


