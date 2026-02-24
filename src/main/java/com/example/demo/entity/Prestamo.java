package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título del libro es obligatorio")
    private String tituloLibro;

    @NotNull(message = "La fecha de préstamo es obligatoria")
    private LocalDate fechaPrestamo;

    private boolean devuelto = false;

    private LocalDate fechaDevolucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno_id", nullable = false)
    private Alumno alumno;

    // --- REGLA DE NEGOCIO CON IF ---

    @AssertTrue(message = "Error en la devolución: Si está devuelto, debe tener fecha y ser posterior al préstamo")
    public boolean isValidacionDevolucion() {
        // 1. Si el libro NO ha sido devuelto, la validación es correcta (pasa)
        if (!this.devuelto) {
            return true;
        }

        // 2. Si el libro SÍ ha sido devuelto...
        if (this.devuelto) {
            // Comprobamos que tenga fecha
            if (this.fechaDevolucion == null) {
                return false; 
            }
            // Comprobamos que la fecha de devolución no sea anterior a la de préstamo
            if (this.fechaDevolucion.isBefore(this.fechaPrestamo)) {
                return false;
            }
        }

        // Si pasa los filtros anteriores, todo es correcto
        return true;
    }

    // --- CONSTRUCTORES, GETTERS Y SETTERS ---

    public Prestamo() {}
    public Prestamo(String tituloLibro, LocalDate fechaPrestamo, Boolean devuelto, LocalDate fechaDevolucion) {
        this.tituloLibro = tituloLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.devuelto = devuelto;
        this.fechaDevolucion = fechaDevolucion;
    }
    
    public Prestamo(String tituloLibro, LocalDate fechaPrestamo, Boolean devuelto, LocalDate fechaDevolucion, Alumno alumno) {
        this.tituloLibro = tituloLibro;
        this.fechaPrestamo = fechaPrestamo;
        this.devuelto = devuelto;
        this.fechaDevolucion = fechaDevolucion;
        this.alumno = alumno;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTituloLibro() { return tituloLibro; }
    public void setTituloLibro(String tituloLibro) { this.tituloLibro = tituloLibro; }

    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }

    public boolean isDevuelto() { return devuelto; }
    public void setDevuelto(boolean devuelto) { this.devuelto = devuelto; }

    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(LocalDate fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }

    public Alumno getAlumno() { return alumno; }
    public void setAlumno(Alumno alumno) { this.alumno = alumno; }
}


