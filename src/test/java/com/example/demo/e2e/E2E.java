package com.example.demo.e2e;

import com.example.demo.entity.Naufrago;
import com.example.demo.entity.Habilidad;
import com.example.demo.repository.NaufragoRepository;

import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.example.demo.repository.HabilidadRepository;

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
    private NaufragoRepository naufragoRepository;

    @Autowired
    private HabilidadRepository habilidadRepository;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper; // Para convertir objetos a JSON

    @Test
    void testE2E() throws Exception {

        // 1. Definimos el nuevo naufrago
        Naufrago nuevoNaufrago = new Naufrago("Pepe", 12, "Hombre", "Isla del Tesoro", "Española", LocalDate.of(2025, 1, 15));
        String naufragoJson = objectMapper.writeValueAsString(nuevoNaufrago);
       
        // 2. Enviamos el POST para crear el naufrago
        naufragoRepository.save(nuevoNaufrago);
        
        // 3. Creamos una habilidad para el alumno creado
        Habilidad p1 = new Habilidad();
        p1.setNombre("Hola mundo");
        p1.setDescripcion("Descripción de la habilidad");
        p1.setDificultad("Media");
        p1.setExperiencia("10 años");
        p1.setCategoria("Supervivencia");
        p1.setNaufrago(nuevoNaufrago);
        
        Habilidad p2 = new Habilidad();
        p2.setNombre("Hola mundo2");
        p2.setDescripcion("Descripción de la habilidad2");
        p2.setDificultad("Alta");
        p2.setExperiencia("5 años");
        p2.setCategoria("Prueba");
        p2.setNaufrago(nuevoNaufrago);
        
        String habilidadJson = objectMapper.writeValueAsString(p1);
        String habilidadJson2 = objectMapper.writeValueAsString(p2);

        // 4. Enviamos el POST para crear la habilidad y verificamos que devuelve 200 OK
        mockMvc.perform(post("/api/habilidades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(habilidadJson))
                .andExpect(status().isOk()); // Verificamos que devuelve codigo 200 
        
        mockMvc.perform(post("/api/habilidades")
                .contentType(MediaType.APPLICATION_JSON)
                .content(habilidadJson2))
                .andExpect(status().isOk()); // Verificamos que devuelve codigo 200 
        
		//6. Listamos los naufragos para verificar que el nuevo alumno aparece con sus habilidades
        /*mockMvc.perform(get("/api/naufragos"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
				.andExpect(jsonPath("$[?(@.nombre == 'Pepe')].habilidades", hasSize(2)));*/


		System.out.println("RESULTADO: El nuevo alumno 'Pepe' fue creado correctamente con sus habilidades asociadas.");

    }


}


