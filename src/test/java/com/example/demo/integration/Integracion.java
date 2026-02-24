package com.example.demo.integration;

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Prestamo;
import com.example.demo.repository.AlumnoRepository;
import com.example.demo.repository.PrestamoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class Integracion {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Test
    void IT1_RepositorioAlumnos_AgrupacionPorCurso() {
        System.out.println("--- [INICIO IT1: Agrupación en H2] ---");

        // 1. Rellenar datos en la BD en memoria
        alumnoRepository.save(new Alumno("Pepe", "1º DAM", "pepe@test.com"));
        alumnoRepository.save(new Alumno("Ana", "1º DAM", "ana@test.com"));
        alumnoRepository.save(new Alumno("Luis", "2º DAM", "luis@test.com"));

        System.out.println("GUARDANDO: 3 alumnos creados en H2.");

        // 2. Obtener todos y agrupar usando lógica de Java (sin SELECT manual)
        
     // 3. Agrupación manual usando un Bucle For
        Map<String, Long> agrupacion = new HashMap<>();
        List<Alumno> todos = alumnoRepository.findAll();
        for (Alumno alumno : todos) {
            String curso = alumno.getCurso();
            
            // Si el curso ya existe, sumamos 1; si no, empezamos en 1
            agrupacion.put(curso, agrupacion.getOrDefault(curso, 0L) + 1);
            
            System.out.println(" -> Procesado: " + alumno.getNombre() + " | Curso: " + curso + 
                               " | Total actual del curso: " + agrupacion.get(curso));
        }

        System.out.println("PROCESANDO RESULTADOS:");
        agrupacion.forEach((curso, conteo) -> 
            System.out.println(" -> Curso: " + curso + " | Cantidad: " + conteo));

        // 3. Validaciones
        assertEquals(2, agrupacion.get("1º DAM"), "Debería haber 2 alumnos en 1º DAM");
        assertEquals(1, agrupacion.get("2º DAM"), "Debería haber 1 alumno en 2º DAM");
        
        System.out.println("--- [FIN IT1] ---\n");
    }

    @Test
    void IT2_RepositorioPrestamos_ConteoConAlumno() {
        System.out.println("--- [INICIO IT2: Integración Préstamo + Alumno] ---");

        // 1. Creamos el Alumno (El dueño del préstamo)
        Alumno alumno1 = new Alumno();
        alumno1.setNombre("Pepe");
        alumno1.setCurso("1º DAM");
        alumno1.setEmail("pepe@test.com");
        
        // Guardamos el alumno primero porque el préstamo lo necesita (FK)
        alumnoRepository.save(alumno1);
        System.out.println("ALUMNO CREADO: " + alumno1.getNombre() + " (ID: " + alumno1.getId() + ")");

        // 2. Creamos los Préstamos por separado y los asignamos al alumno
        Prestamo p1 = new Prestamo();
        p1.setTituloLibro("Libro A");
        p1.setFechaPrestamo(LocalDate.now());
        p1.setDevuelto(true);
        p1.setFechaDevolucion(LocalDate.now());
        p1.setAlumno(alumno1); // <-- Vinculamos con el alumno

        Prestamo p2 = new Prestamo();
        p2.setTituloLibro("Libro B");
        p2.setFechaPrestamo(LocalDate.now());
        p2.setDevuelto(false);
        p2.setFechaDevolucion(null);
        p2.setAlumno(alumno1);

        System.out.println("RELLENANDO PRESTAMOS: '" + p1.getTituloLibro() + "' (Devuelto) y '" + 
                           p2.getTituloLibro() + "' (Pendiente)");

        // 3. Guardamos los préstamos
        prestamoRepository.save(p1);
        prestamoRepository.save(p2);

        // 4. Verificación y Conteo (USANDO FOR EN LUGAR DE STREAMS)
        List<Prestamo> todos = prestamoRepository.findAll();
        
        long devueltos = 0;
        long asociadosAPepe = 0;

        for (Prestamo p : todos) {
            // Conteo de devueltos
            if (p.isDevuelto()) {
                devueltos++;
            }
            
            // Conteo de asociados a Pepe
            if (p.getAlumno() != null && p.getAlumno().getNombre().equals("Pepe")) {
                asociadosAPepe++;
            }
        }

        System.out.println("RESULTADOS EN H2:");
        System.out.println(" -> Total devueltos: " + devueltos);
        System.out.println(" -> Total préstamos de Pepe: " + asociadosAPepe);

        // VALIDACIONES
        assertEquals(1, devueltos, "Debería haber 1 préstamo devuelto");
        assertEquals(2, asociadosAPepe, "Pepe debería tener 2 préstamos en total");
        
        System.out.println("--- [FIN IT2] ---\n");
    }
}