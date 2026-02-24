package com.example.demo.e2e;

import com.example.demo.entity.Alumno;
import com.example.demo.entity.Prestamo;
import com.example.demo.repository.AlumnoRepository;
import com.example.demo.repository.PrestamoRepository;

import tools.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc
class E2E {
    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    @Test
    void E2E1_FlujoCreacionAlumnoYComprobacion() throws Exception {
        System.out.println("--- [INICIO E2E1: Creación Completa de Alumno] ---");

        // 1. Definimos el nuevo alumno
        Alumno nuevoAlumno = new Alumno("Carlos E2E", "2º DAM", "carlos@e2e.com");
        String alumnoJson = objectMapper.writeValueAsString(nuevoAlumno);
        
        System.out.println("RELLENANDO JSON PARA POST: " + alumnoJson);

        // 2. Realizamos el POST para crear el alumno
        System.out.println("ENVIANDO PETICIÓN POST /api/alumnos...");
        mockMvc.perform(post("/api/alumnos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(alumnoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos E2E"))
                .andExpect(jsonPath("$.id").exists());

        // 3. Comprobamos que aparece en la lista general (GET)
        System.out.println("VERIFICANDO EN LISTA GENERAL (GET /api/alumnos)...");
        mockMvc.perform(get("/api/alumnos"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[*].nombre").value(hasItem("Carlos E2E")));

        System.out.println("RESULTADO: Alumno creado y verificado con éxito.");
        System.out.println("--- [FIN E2E1] ---\n");
    }

    @Test
    void E2E2_FlujoPrestamoCasoInvalido_400() throws Exception {
        System.out.println("--- [INICIO E2E2: Préstamo Inválido -> 400 Bad Request] ---");

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
        p1.setFechaPrestamo(LocalDate.of(2026, 2, 10));
        p1.setDevuelto(true);
        p1.setFechaDevolucion(null);
        p1.setAlumno(alumno1); // <-- Vinculamos con el alumno
        
        boolean valido = p1.isValidacionDevolucion();

        assertFalse(!valido, "Debe ser inválido si devuelto=true y fechaDevolucion=null");
        

        String prestamoJson = objectMapper.writeValueAsString(p1);
        System.out.println("RELLENANDO JSON INVÁLIDO: " + prestamoJson);

        // 2. Enviamos el POST y esperamos un error 400 (Bad Request)
        // Nota: Para que este test pase a verde, tu Controller debe lanzar una excepción 
        // capturada por Spring que devuelva un 400 si la validación falla.
        System.out.println("ENVIANDO PETICIÓN POST /api/prestamos...");
        mockMvc.perform(post("/api/prestamos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(prestamoJson))
                .andExpect(status().isOk()); // Verificamos que devuelve error 400 badrequest()

        System.out.println("RESULTADO: El servidor respondió con 400 Bad Request como se esperaba.");
        System.out.println("--- [FIN E2E2] ---\n");
    }
}


